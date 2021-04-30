package com.charlie.zoo.serviceImpl;


import com.charlie.zoo.entity.*;
import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.enums.StatusOfOrder;
import com.charlie.zoo.enums.StatusOfPayment;
import com.charlie.zoo.enums.UserRole;
import com.charlie.zoo.jpa.OrderJPA;
import com.charlie.zoo.service.*;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderJPA orderJPA;
    private final OrderDetailsService orderDetailsService;
    private final PhoneService phoneService;
    private final ClientService clientService;
    private final UsersService usersService;

    public OrderServiceImpl(OrderJPA orderJPA, @Lazy OrderDetailsService orderDetailsService, PhoneService phoneService, ClientService clientService, UsersService usersService) {
        this.orderJPA = orderJPA;
        this.orderDetailsService = orderDetailsService;
        this.phoneService = phoneService;
        this.clientService = clientService;
        this.usersService = usersService;
    }


    @Override
    public OrderInfo save(OrderInfo orderInfo)
    {
        Users user = usersService.getAuth(SecurityContextHolder.getContext().getAuthentication());
        orderInfo.setCreatedBy(user);
        orderInfo.setSumPrice(getSummaryPrice(findById(orderInfo.getId())));
        return orderJPA.save(orderInfo);
    }

    @Override
    public OrderInfo update(OrderInfo order) {
        OrderInfo orderDB = findById(order.getId());
        orderDB.setDate(order.getDate());
        orderDB.setNameOfClient(order.getNameOfClient());
        orderDB.setPhone(order.getPhone());
        orderDB.setDescription(order.getDescription());

        orderDB.setLvivDelivering(order.isLvivDelivering());
        orderDB.setNovaPoshtaDelivering(order.isNovaPoshtaDelivering());
        orderDB.setPayByCard(order.isPayByCard());
        orderDB.setPayByCash(order.isPayByCash());

        orderDB.setOpt(order.isOpt());
        System.out.println("opt "+order.isOpt());
        orderDB.setPayment(order.getPayment());
        orderDB.setStatusOfOrder(order.getStatusOfOrder());

        for(OrderDetails details:orderDB.getOrderDetails()) {
            details.setOrderInfo(orderDB);
            orderDetailsService.pinPriceOfProduct(details);
        }

        if(orderDB.getCreatedBy()==null){
            Users user = usersService.getAuth(SecurityContextHolder.getContext().getAuthentication());
            orderDB.setCreatedBy(user);
        }
        return save(orderDB);
    }


    public OrderInfo submitOrder(OrderInfo order) {
        OrderInfo  orderDB = findById(order.getId());

        orderDB.setDate(LocalDateTime.now().format(DateTimeFormatter.ISO_DATE));

        orderDB.setNameOfClient(order.getNameOfClient());

        Client client = clientService.validate(order);
        Phone phone = order.getPhone();
        phone.setClient(client);

        orderDB.setPhone(phoneService.save(phone));
        orderDB.setDescription(order.getDescription());

        orderDB.setLvivDelivering(order.isLvivDelivering());
        orderDB.setNovaPoshtaDelivering(order.isNovaPoshtaDelivering());
        orderDB.setPayByCard(order.isPayByCard());
        orderDB.setPayByCash(order.isPayByCash());

        for(OrderDetails details:orderDB.getOrderDetails()) {
            orderDetailsService.pinPriceOfProduct(details);
        }

        orderDB.setPayment(StatusOfPayment.WAIT_FOR_PAYMENT);
        orderDB.setStatusOfOrder(StatusOfOrder.NEW);
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
        if (orderInfo== null || orderInfo.getOrderDetails()==null || orderInfo.getOrderDetails().size()==0){
            return 0;
        }
        if(!orderInfo.getId().toString().isEmpty()) {
            orderInfo = findById(orderInfo.getId());
        }
        for(OrderDetails details:orderInfo.getOrderDetails()){
            double count = details.getCount();
            double tempSum = details.getPrice().doubleValue()*count;
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
        return orderJPA.findAll(Sort.by(Sort.Direction.DESC,"date"));
    }

    @Override
    public List<OrderInfo> findByStatusAndUser(String[] statuses, int id) {
        List<StatusOfPayment> statusOfPayments = new ArrayList<>();
        List<StatusOfOrder> statusOfOrders = new ArrayList<>();

        if(statuses!=null && statuses.length>0) {
            for (String st : statuses) {
                switch (st) {
                    case "NOT_SUBMITTED":
                        statusOfPayments.add(StatusOfPayment.NOT_SUBMITTED);
                        break;
                    case "WAIT_FOR_PAYMENT":
                        statusOfPayments.add(StatusOfPayment.WAIT_FOR_PAYMENT);
                        break;
                    case "PAYMENT_BY_CASH":
                        statusOfPayments.add(StatusOfPayment.PAYMENT_BY_CASH);
                        break;
                    case "SUBMITTED":
                        statusOfPayments.add(StatusOfPayment.SUBMITTED);
                        break;

                    case "NEW":
                        statusOfOrders.add(StatusOfOrder.NEW);
                        break;
                    case "CANCELLED":
                        statusOfOrders.add(StatusOfOrder.CANCELLED);
                        break;
                    case "DELIVERED":
                        statusOfOrders.add(StatusOfOrder.DELIVERED);
                        break;
                    case "FINISHED":
                        statusOfOrders.add(StatusOfOrder.FINISHED);
                        break;
                }
            }
        }
        Users user = usersService.findById(id);
        if(user==null) return new ArrayList<>();
        if(user.getRole()!=UserRole.ROLE_ADMIN) {
            if (statusOfPayments.size() > 0 && statusOfOrders.size() > 0) {
                return new ArrayList<>(orderJPA.findByStatusOfOrderInAndPaymentInAndStatusOfEntityAndCreatedById(statusOfOrders, statusOfPayments, StatusOfEntity.ACTIVE, id));
            }
            if (statusOfPayments.size() > 0 && statusOfOrders.size() == 0) {
                return new ArrayList<>(orderJPA.findByPaymentInAndStatusOfEntityAndCreatedById(statusOfPayments, StatusOfEntity.ACTIVE, id));
            }
            if (statusOfPayments.size() == 0 && statusOfOrders.size() > 0) {
                return new ArrayList<>(orderJPA.findByStatusOfOrderInAndStatusOfEntityAndCreatedById(statusOfOrders, StatusOfEntity.ACTIVE, id));
            }
            return new ArrayList<>(orderJPA.findByStatusOfOrderInAndPaymentInAndStatusOfEntityAndCreatedById(Arrays.asList(StatusOfOrder.values()),Arrays.asList(StatusOfPayment.values()),StatusOfEntity.ACTIVE,id));
        }else {
            if (statusOfPayments.size() > 0 && statusOfOrders.size() > 0) {
                return new ArrayList<>(orderJPA.findByStatusOfOrderInAndPaymentInAndStatusOfEntity(statusOfOrders, statusOfPayments, StatusOfEntity.ACTIVE));
            }
            if (statusOfPayments.size() > 0 && statusOfOrders.size() == 0) {
                return new ArrayList<>(orderJPA.findByPaymentInAndStatusOfEntity(statusOfPayments, StatusOfEntity.ACTIVE));
            }
            if (statusOfPayments.size() == 0 && statusOfOrders.size() > 0) {
                return new ArrayList<>(orderJPA.findByStatusOfOrderInAndStatusOfEntity(statusOfOrders, StatusOfEntity.ACTIVE));
            }
            return new ArrayList<>(orderJPA.findByStatusOfOrderInAndPaymentInAndStatusOfEntity(Arrays.asList(StatusOfOrder.values()),Arrays.asList(StatusOfPayment.values()),StatusOfEntity.ACTIVE));
        }
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
