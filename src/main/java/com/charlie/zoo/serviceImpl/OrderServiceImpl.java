package com.charlie.zoo.serviceImpl;


import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.enums.StatusOfPayment;
import com.charlie.zoo.jpa.OrderJPA;
import com.charlie.zoo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return orderJPA.save(orderInfo);
    }

    @Override
    public OrderInfo submitOrder(OrderInfo order, String name, String phone, String description) {
        order.setNameOfClient(name);
        order.setPhone(phone);
        order.setDescription(description);
        order.setStatusOfEntity(StatusOfEntity.SUBMITTED);
        return save(order);
    }

    @Override
    public OrderInfo checkOrder(Map<String,String> data){
        String orderId = data.get("order_id");
        OrderInfo orderInfo = findById(UUID.fromString(orderId));
        if (orderInfo!=null){
            String status = data.get("status");
            System.out.println("Status "+status);
            if(status.equals("success")){
                orderInfo.setPayment(StatusOfPayment.SUBMITTED);
            }else{
                orderInfo.setPayment(StatusOfPayment.WAIT_FOR_PAYING);
            }
            return save(orderInfo);
        }
        return null;
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
