package com.charlie.zoo.serviceImpl;


import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.jpa.OrderDetailsJPA;
import com.charlie.zoo.service.OrderDetailsService;
import com.charlie.zoo.service.OrderService;
import com.charlie.zoo.service.PackageTypeService;
import com.charlie.zoo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class OrderDetailsServiceImpl implements OrderDetailsService {
    private final OrderDetailsJPA detailsJPA;
    private final OrderService orderService;
    private final PackageTypeService packageTypeService;


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
    public OrderDetails addProductToOrder(UUID orderId, int packageId, int count) {
        OrderDetails orderDetails = findByOrderInfoIdAndOrderByPackageId(orderId,packageId);
        if (orderDetails==null){
            orderDetails = new OrderDetails();
            orderDetails.setOrderInfo(orderService.findById(orderId));
            orderDetails.setPackageType(packageTypeService.findById(packageId));
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
    public void changeCount(int id, int count){
        OrderDetails details = findById(id);
        if(details!=null){
            details.setCount(count);
            save(details);
        }
    }

    @Override
    public void deleteProductFromOrder(UUID orderId, int packageId) {
        OrderDetails orderDetails = findByOrderInfoIdAndOrderByPackageId(orderId,packageId);
        if(orderDetails!=null){
            deleteByID(orderDetails.getId());
        }
    }

    @Override
    public OrderDetails findById(int id) {
        return detailsJPA.findById(id).orElse(null);
    }

    @Override
    public OrderDetails findByOrderInfoIdAndOrderByPackageId(UUID orderId, int packageId) {
        return detailsJPA.findFirstByByOrderInfoByPackage(orderId,packageId);
    }

    @Override
    public List<OrderDetails> findAll() {
        return detailsJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        detailsJPA.deleteById(id);
    }

    @Override
    public void delete(Integer id, String uuid) {
        OrderDetails details = findById(id);
        if (details.getOrderInfo().getId().equals(UUID.fromString(uuid))){
            deleteByID(id);
        }
    }
}
