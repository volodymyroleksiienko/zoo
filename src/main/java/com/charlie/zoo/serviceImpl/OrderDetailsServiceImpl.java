package com.charlie.zoo.serviceImpl;


import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.jpa.OrderDetailsJPA;
import com.charlie.zoo.service.OrderDetailsService;
import com.charlie.zoo.service.OrderService;
import com.charlie.zoo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private OrderDetailsJPA detailsJPA;
    private OrderService orderService;
    private ProductService productService;


    @Override
    public OrderDetails save(OrderDetails orderDetails) {
        return detailsJPA.save(orderDetails);
    }

    @Override
    public void save(List<Integer> id, List<Integer> count) {
        if(id.size()==count.size()){
            for(int i=0;i<id.size();i++){
                OrderDetails orderDetails = findById(id.get(i));
                if(orderDetails!=null){
                    orderDetails.setCount(count.get(i));
                    save(orderDetails);
                }
            }
        }
    }

    @Override
    public OrderDetails update(OrderDetails orderDetails) {
        return save(orderDetails);
    }

    @Override
    public OrderDetails addProductToOrder(UUID orderId, int productId,int sizeId, int count) {
        OrderDetails orderDetails = findByOrderInfoIdAndOrderByProductId(orderId,productId);
        if (orderDetails==null){
            orderDetails = new OrderDetails();
            orderDetails.setOrderInfo(orderService.findById(orderId));
            orderDetails.setProduct(productService.findById(productId));
            if(count<=0) {
                orderDetails.setCount(1);
            }else{
                orderDetails.setCount(count);
            }
        }else{
            if(count>0) {
                orderDetails.setCount(orderDetails.getCount() + count);
            }
        }

        return save(orderDetails);
    }

    @Override
    public void deleteProductFromOrder(UUID orderId, int productId) {
        OrderDetails orderDetails = findByOrderInfoIdAndOrderByProductId(orderId,productId);
        if(orderDetails!=null){
            deleteByID(orderDetails.getId());
        }
    }

    @Override
    public OrderDetails findById(int id) {
        return detailsJPA.findById(id).orElse(null);
    }

    @Override
    public OrderDetails findByOrderInfoIdAndOrderByProductId(UUID orderId, int productId) {
        return detailsJPA.findByOrderInfoByProduct(orderId,productId);
    }

    @Override
    public List<OrderDetails> findAll() {
        return detailsJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        detailsJPA.deleteById(id);
    }
}
