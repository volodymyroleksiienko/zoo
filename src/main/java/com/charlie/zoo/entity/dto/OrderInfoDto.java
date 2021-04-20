package com.charlie.zoo.entity.dto;

import com.charlie.zoo.entity.OrderInfo;
import lombok.Data;

import java.util.List;

@Data
public class OrderInfoDto {
    private String id;

    private String date;
    private String nameOfClient;
    private String phone;
    private String description;
    private double sumPrice;

    private boolean lvivDelivering;
    private boolean novaPoshtaDelivering;
    private boolean payByCard;
    private boolean payByCash;
    private boolean opt;

    private List<OrderDetailsDto> orderDetails;
    private String payment;
    private String statusOfOrder;
    private String statusOfEntity;


    public static OrderInfoDto convertToDto(OrderInfo order){
        OrderInfoDto dto =  new OrderInfoDto();
        dto.setId(order.getId().toString());
        dto.setDate(order.getDate());
        dto.setNameOfClient(order.getNameOfClient());
        if(order.getPhone()!=null) {
            dto.setPhone(order.getPhone().getPhone());
        }
        dto.setDescription(order.getDescription());
        dto.setSumPrice(order.getSumPrice());

        dto.setLvivDelivering(order.isLvivDelivering());
        dto.setNovaPoshtaDelivering(order.isNovaPoshtaDelivering());
        dto.setPayByCard(order.isPayByCard());
        dto.setPayByCash(order.isPayByCash());
        if(order.getOpt()!=null) {
            dto.setOpt(order.getOpt());
        }
        dto.setOrderDetails(OrderDetailsDto.convertToListDto(order.getOrderDetails()));
        dto.setPayment(order.getPayment().name());
        dto.setStatusOfOrder(order.getStatusOfOrder().name());
        dto.setStatusOfEntity(order.getStatusOfEntity().name());
        return dto;
    }
}
