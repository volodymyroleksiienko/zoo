package com.charlie.zoo.entity;

import com.charlie.zoo.entity.dto.PackageTypeDto;
import com.charlie.zoo.enums.StatusOfEntity;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PackageType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal price=new BigDecimal("0.00");
    private BigDecimal newPrice=new BigDecimal("0.00");
    private BigDecimal discount=new BigDecimal("0.00");


    private BigDecimal wholeSalePrice=new BigDecimal("0.00");
    private BigDecimal wholeSaleNewPrice=new BigDecimal("0.00");
    private BigDecimal wholeSaleDiscount=new BigDecimal("0.00");
    private boolean wholeSaleOnSale;
    private boolean wholeSaleStatus;

    private BigDecimal packSize=new BigDecimal("0.00");
    private String packType;
    private boolean onSale=false;
    private String producerFactoryId;

    private int countOfProduct;

    @ManyToOne
    private Product product;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;

    @Override
    public String toString() {
        return new Gson().toJson(PackageTypeDto.convertToDto(this));
    }
}
