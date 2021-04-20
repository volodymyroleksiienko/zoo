package com.charlie.zoo.entity.dto;

import com.charlie.zoo.entity.OrderDetails;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderDetailsDto {
    int id;

    private int count;
    private double price;
    private double discount;
    private boolean onSale;
    private boolean opt;
    private String lastChangeDate;

    private PackageTypeDto packageTypeDto;
    private String  orderInfoId;

    public static OrderDetailsDto convertToDto(OrderDetails details){
        OrderDetailsDto dto =  new OrderDetailsDto();
        dto.setId(details.getId());
        dto.setCount(details.getCount());
        dto.setPrice(details.getPrice().doubleValue());
        dto.setDiscount(details.getDiscount().doubleValue());
        if(details.getOnSale()!=null) {
            dto.setOnSale(details.getOnSale());
        }
        if(details.getOpt()!=null){
            dto.setOpt(details.getOpt());
        }

        dto.setLastChangeDate(details.getLastChangeDate());
        dto.setPackageTypeDto(PackageTypeDto.convertToDto(details.getPackageType()));
        dto.setOrderInfoId(details.getOrderInfo().getId().toString());
        return dto;
    }

    public static List<OrderDetailsDto> convertToListDto(List<OrderDetails> details){
        return details.stream().map(OrderDetailsDto::convertToDto).collect(Collectors.toList());
    }
}
