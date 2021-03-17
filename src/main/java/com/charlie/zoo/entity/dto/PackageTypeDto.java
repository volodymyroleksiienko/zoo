package com.charlie.zoo.entity.dto;

import com.charlie.zoo.entity.Product;
import com.charlie.zoo.enums.StatusOfEntity;

import javax.persistence.*;
import java.math.BigDecimal;

public class PackageTypeDto {
    private int id;

    private BigDecimal price;
    private BigDecimal newPrice;
    private BigDecimal packSize;
    private String packType;
    private boolean onSale;


    private int countOfProduct;



    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;
}
