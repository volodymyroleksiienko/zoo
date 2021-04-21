package com.charlie.zoo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String date;
    @ManyToOne
    private PackageType packageType;

    private double price;
    private int count;
    private double sum;
    private String producer;
}
