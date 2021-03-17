package com.charlie.zoo.entity;

import com.charlie.zoo.enums.StatusOfEntity;
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
    private String packType;
    private boolean onSale;


    private int countOfProduct;

    @ManyToOne
    private Product product;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;
}
