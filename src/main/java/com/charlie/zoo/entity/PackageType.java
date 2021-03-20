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

    private BigDecimal price;
    private BigDecimal newPrice;
    private BigDecimal packSize;
    private BigDecimal discount;
    private String packType;
    private boolean onSale;


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
