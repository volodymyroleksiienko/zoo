package com.charlie.zoo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class HistoryDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double price;

    @Override
    public String toString() {
        return "HistoryDetails{" +
                "id=" + id +
                ", price=" + price +
                ", count=" + count +
                ", sum=" + sum +
                '}';
    }

    private int count;
    private double sum;

    @ManyToOne
    private PackageType packageType;
    @ManyToOne
    private ProductHistory productHistory;
}
