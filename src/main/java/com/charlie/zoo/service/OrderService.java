package com.charlie.zoo.service;



import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.enums.StatusOfEntity;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {
    OrderInfo save(OrderInfo orderInfo);
    OrderInfo submitOrder(OrderInfo order);
    OrderInfo checkOrder(Map<String,String> data);
    OrderInfo findById(UUID id);
    List<OrderInfo> findAll();
    List<OrderInfo> findByStatus(StatusOfEntity status);
    void deleteByID(UUID id);
}
