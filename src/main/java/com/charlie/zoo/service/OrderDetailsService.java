package com.charlie.zoo.service;



import com.charlie.zoo.entity.OrderDetails;

import java.util.List;
import java.util.UUID;

public interface OrderDetailsService {
    OrderDetails save(OrderDetails orderDetails);
    void save(List<Integer> id, List<Integer> count);
    OrderDetails update(OrderDetails orderDetails);
    OrderDetails addProductToOrder(UUID orderId, int packageId, int count);
    void deleteProductFromOrder(UUID orderId, int packageId);
    OrderDetails findById(int id);
    OrderDetails findByOrderInfoIdAndOrderByPackageId(UUID orderId, int packageId);
    List<OrderDetails> findAll();
    void deleteByID(int id);
    void delete(Integer id, String uuid);
}
