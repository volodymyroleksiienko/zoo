package com.charlie.zoo.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class PackageType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private BigDecimal price;
    private BigDecimal packSize;
    private boolean onSale;
    private BigDecimal newPrice;

    private int countOfProduct;

    @ManyToOne
    private Product product;
}
