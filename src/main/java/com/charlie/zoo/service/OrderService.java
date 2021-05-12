package com.charlie.zoo.service;



import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.ProductHistory;
import com.charlie.zoo.entity.Users;
import com.charlie.zoo.enums.StatusOfEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {
    OrderInfo save(OrderInfo orderInfo);
    OrderInfo update(OrderInfo orderInfo);
    OrderInfo submitOrder(OrderInfo order);
    OrderInfo checkStatus(String id,String statusOfPayment, String statusOfOrder);
    OrderInfo checkOrder(Map<String,String> data);
    List<OrderInfo> findByDateBetween(String from, String to,List<Users> createdBy);
    List<OrderInfo> findByDateBefore(String from, List<Users> createdBy);
    double getSummaryPrice(OrderInfo orderInfo);
    OrderInfo findById(UUID id);
    List<OrderInfo> findAll();
    List<OrderInfo> findByStatusAndUser(String[] statuses, int id);
    List<OrderInfo> findByStatus(StatusOfEntity status);
    void deleteByID(UUID id);
}
