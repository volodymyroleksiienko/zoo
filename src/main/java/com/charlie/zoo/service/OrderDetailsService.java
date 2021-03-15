package com.charlie.zoo.service;



import com.charlie.zoo.entity.OrderDetails;

import java.util.List;
import java.util.UUID;

public interface OrderDetailsService {
    OrderDetails save(OrderDetails orderDetails);
    void save(List<Integer> id, List<Integer> count);
    OrderDetails update(OrderDetails orderDetails);
    OrderDetails addProductToOrder(UUID orderId, int productId,int sizeId, int count);
    void deleteProductFromOrder(UUID orderId, int productId);
    OrderDetails findById(int id);
    OrderDetails findByOrderInfoIdAndOrderByProductId(UUID orderId, int productId);
    List<OrderDetails> findAll();
    void deleteByID(int id);
}
