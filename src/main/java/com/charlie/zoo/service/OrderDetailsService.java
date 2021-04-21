package com.charlie.zoo.service;



import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.OrderInfo;

import java.util.List;
import java.util.UUID;

public interface OrderDetailsService {
    OrderDetails save(OrderDetails orderDetails);
    void save(List<Integer> id, List<Integer> count);
    OrderDetails update(OrderDetails orderDetails);
    void changeCount(int id, int count);
    OrderDetails addProductToOrder(UUID orderId, int packageId, int count);
    void deleteProductFromOrder(UUID orderId, int packageId);
    OrderDetails pinPriceOfProduct(OrderDetails details);
    OrderDetails findById(int id);
    OrderDetails findByOrderInfoIdAndOrderByPackageId(UUID orderId, int packageId);
    List<OrderDetails> findAll();
    void deleteByID(int id);
    OrderInfo delete(Integer id, String uuid);
}
