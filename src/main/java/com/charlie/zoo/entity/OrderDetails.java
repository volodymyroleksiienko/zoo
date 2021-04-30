package com.charlie.zoo.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private int count;
    private BigDecimal price=new BigDecimal("0");
    private BigDecimal discount=new BigDecimal("0");
    private Boolean onSale;
    private Boolean opt;
    private String lastChangeDate;

    @ManyToOne
    private PackageType packageType;
    @ManyToOne
    private OrderInfo orderInfo;

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                "opt=" + opt +
                "price=" + price +
                ", count=" + count +
                '}';
    }
}
