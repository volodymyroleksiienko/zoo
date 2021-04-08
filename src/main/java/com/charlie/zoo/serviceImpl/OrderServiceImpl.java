package com.charlie.zoo.serviceImpl;


import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.enums.StatusOfOrder;
import com.charlie.zoo.enums.StatusOfPayment;
import com.charlie.zoo.jpa.OrderJPA;
import com.charlie.zoo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderJPA orderJPA;

    @Autowired
    public OrderServiceImpl(OrderJPA orderJPA) {
        this.orderJPA = orderJPA;
    }

    @Override
    public OrderInfo save(OrderInfo orderInfo)
    {
        orderInfo.setSumPrice(getSummaryPrice(orderInfo));
        return orderJPA.save(orderInfo);
    }


    public OrderInfo submitOrder(OrderInfo order) {
        OrderInfo  orderDB = findById(order.getId());

        orderDB.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));

        orderDB.setNameOfClient(order.getNameOfClient());
        orderDB.setPhone(order.getPhone());
        orderDB.setDescription(order.getDescription());

        orderDB.setLvivDelivering(order.isLvivDelivering());
        orderDB.setNovaPoshtaDelivering(order.isNovaPoshtaDelivering());
        orderDB.setPayByCard(order.isPayByCard());
        orderDB.setPayByCash(order.isPayByCash());

        orderDB.setPayment(StatusOfPayment.WAIT_FOR_PAYMENT);
        return save(orderDB);
    }

    @Override
    public OrderInfo checkStatus(String id,String statusOfPayment, String statusOfOrder) {
         OrderInfo orderInfo = findById(UUID.fromString(id));
         if(orderInfo!=null){
             switch (statusOfPayment){
                 case "NOT_SUBMITTED": orderInfo.setPayment(StatusOfPayment.NOT_SUBMITTED); break;
                 case "WAIT_FOR_PAYMENT": orderInfo.setPayment(StatusOfPayment.WAIT_FOR_PAYMENT); break;
                 case "PAYMENT_BY_CASH": orderInfo.setPayment(StatusOfPayment.PAYMENT_BY_CASH); break;
                 case "SUBMITTED": orderInfo.setPayment(StatusOfPayment.SUBMITTED); break;
             }
             switch (statusOfOrder){
                 case "NEW":orderInfo.setStatusOfOrder(StatusOfOrder.NEW); break;
                 case "CANCELLED":orderInfo.setStatusOfOrder(StatusOfOrder.CANCELLED); break;
                 case "DELIVERED":orderInfo.setStatusOfOrder(StatusOfOrder.DELIVERED); break;
                 case "FINISHED":orderInfo.setStatusOfOrder(StatusOfOrder.FINISHED); break;
             }
             return save(orderInfo);
         }
         return null;
    }


    @Override
    public OrderInfo checkOrder(Map<String,String> data){
        System.out.println(data);
        String orderId = data.get("order_id");
        OrderInfo orderInfo = findById(UUID.fromString(orderId));
        if (orderInfo!=null){
            String status = data.get("status");
            System.out.println("Status "+status);
            if(status !=null && status.equals("success")){
                orderInfo.setPayment(StatusOfPayment.SUBMITTED);
            }else{
                orderInfo.setPayment(StatusOfPayment.WAIT_FOR_PAYMENT);
            }
            return save(orderInfo);
        }
        return null;
    }

    @Override
    public double getSummaryPrice(OrderInfo orderInfo) {
        double sum = 0;
        if (orderInfo.getOrderDetails()==null || orderInfo.getOrderDetails().size()==0){
            return 0;
        }
        for(OrderDetails details:orderInfo.getOrderDetails()){
            PackageType type = details.getPackageType();
            double count = details.getCount();
            double tempSum = 0;
            if(type.isOnSale()){
                tempSum=count*type.getNewPrice().doubleValue();
            }else{
                tempSum=count*type.getCountOfProduct()*type.getPrice().doubleValue();
            }
            sum = sum + tempSum;
        }
        return sum;
    }

    @Override
    public OrderInfo findById(UUID id) {
        return orderJPA.findById(id).orElse(null);
    }

    @Override
    public List<OrderInfo> findAll() {
        return orderJPA.findAll();
    }

    @Override
    public List<OrderInfo> findByStatus(StatusOfEntity status) {
        return orderJPA.findByStatus(status);
    }

    @Override
    public void deleteByID(UUID id) {
        orderJPA.deleteById(id);
    }
}
