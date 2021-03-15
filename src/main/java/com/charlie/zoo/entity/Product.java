package com.charlie.zoo.entity;

import com.charlie.zoo.enums.StatusOfEntity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String description;
    private BigDecimal price;

    private boolean onSale;
    private BigDecimal newPrice;

    private int countOfProduct;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] img;
    private String imgType;
    private String imgName;

    @ManyToOne
    private Producer producer;

    @ManyToOne
    private CategoryItem categoryItem;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity=StatusOfEntity.ACTIVE;
}
