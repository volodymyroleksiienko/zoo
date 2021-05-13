package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.*;
import com.charlie.zoo.entity.dto.StatisticDto;
import com.charlie.zoo.enums.StatusOfOrder;
import com.charlie.zoo.enums.StatusOfPayment;
import com.charlie.zoo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@AllArgsConstructor
public class StatisticServiceImpl implements StatisticService {
    private final OrderService orderService;
    private final ProductHistoryService productHistoryService;
    private final PackageTypeService packageTypeService;
    private final UsersService usersService;


    @Override
    public List<StatisticDto> getStatistic(String from, String to,Integer[] users) throws ParseException {
        if(from.isEmpty() || to.isEmpty()) return new ArrayList<>();
        List<Users> usersList;
        if(users!=null && users.length>0) {
            usersList = usersService.findById(users);
        }else {
            usersList = usersService.findAll();
            usersList.add(null);
        }
        List<StatisticDto> statisticDtos = new ArrayList<>();
        List<OrderInfo> orderInfos = orderService.findByDateBetween(from,to,usersList);
        List<OrderDetails> details = orderInfos.parallelStream()
                .filter(order -> (order.getPayment().equals(StatusOfPayment.SUBMITTED)) ||
                        (order.getStatusOfOrder().equals(StatusOfOrder.DELIVERED) ||
                                order.getStatusOfOrder().equals(StatusOfOrder.FINISHED)))
                .flatMap(orderInfo -> orderInfo.getOrderDetails().stream())
                .collect(Collectors.toList());

        List<ProductHistory> productHistories = productHistoryService.findByDateBetween(from,to);
        List<HistoryDetails> productDetails = productHistories.parallelStream()
                .flatMap(p -> p.getHistoryDetails().stream())
                .collect(Collectors.toList());


        List<OrderInfo> orderInfosBefore = orderService.findByDateBefore(from,usersList);
        List<OrderDetails> detailsBefore = orderInfosBefore.parallelStream()
                .filter(order -> (order.getPayment().equals(StatusOfPayment.SUBMITTED)) ||
                        (order.getStatusOfOrder().equals(StatusOfOrder.DELIVERED) ||
                                order.getStatusOfOrder().equals(StatusOfOrder.FINISHED)))
                .flatMap(orderInfo -> orderInfo.getOrderDetails().stream())
                .collect(Collectors.toList());

        List<ProductHistory> productHistoriesBefore = productHistoryService.findByDateBefore(from);
        List<HistoryDetails> productDetailsBefore = productHistoriesBefore.parallelStream()
                .flatMap(p -> p.getHistoryDetails().stream())
                .collect(Collectors.toList());



        List<PackageType> packageTypes = packageTypeService.findAll();
        for(PackageType type:packageTypes){
            List<OrderDetails> packDetails = details.parallelStream()
                    .filter(details1 -> details1.getPackageType().getId()==type.getId())
                    .collect(Collectors.toList());

            List<HistoryDetails> packHistory = productDetails.parallelStream()
                    .filter(details1 -> details1.getPackageType().getId()==type.getId())
                    .collect(Collectors.toList());

            List<OrderDetails> packDetailsBefore = detailsBefore.parallelStream()
                    .filter(details1 -> details1.getPackageType().getId()==type.getId())
                    .collect(Collectors.toList());

            List<HistoryDetails> packHistoryBefore = productDetailsBefore.parallelStream()
                    .filter(details1 -> details1.getPackageType().getId()==type.getId())
                    .collect(Collectors.toList());

            if(packDetails.size() ==0 && packHistory.size()==0) continue;

            int getCount = packHistory.stream().flatMap(details1 -> Stream.of(details1.getCount())).reduce(Integer::sum).orElse(0);
            int selCount = packDetails.stream().flatMap(details1 -> Stream.of(details1.getCount())).reduce(Integer::sum).orElse(0);

            int getCountBefore = packHistoryBefore.stream().flatMap(details1 -> Stream.of(details1.getCount())).reduce(Integer::sum).orElse(0);
            int selCountBefore = packDetailsBefore.stream().flatMap(details1 -> Stream.of(details1.getCount())).reduce(Integer::sum).orElse(0);
            int beforeCount = getCountBefore-selCountBefore;

            double getEarnSum = packDetails.stream().flatMap(d-> Stream.of(d.getCount()*d.getPrice().doubleValue()))
                    .reduce(Double::sum).orElse(0d);
            double getSpentSum = packHistory.stream().flatMap(d-> Stream.of(d.getCount()*d.getPrice()))
                    .reduce(Double::sum).orElse(0d);

            StatisticDto statisticDto = new StatisticDto();
            statisticDto.setNameOfProduct(type.getProduct().getName());
            statisticDto.setPackageType(type.getPackSize().doubleValue()+type.getPackType());
            statisticDto.setGetCount(getCount);
            statisticDto.setSellCount(selCount);
            statisticDto.setBeforeCount(getCountBefore);
            statisticDto.setTotalCount(getCountBefore+getCount-selCount);


            statisticDto.setEarnMoney(new BigDecimal(getEarnSum));
            statisticDto.setSpendMoney(new BigDecimal(getSpentSum));
            statisticDto.setTotalMoney(new BigDecimal(beforeCount));
            statisticDtos.add(statisticDto);
        }
        return statisticDtos;


    }
}
