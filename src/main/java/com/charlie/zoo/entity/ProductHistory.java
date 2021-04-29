package com.charlie.zoo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String date;
    private double sum;
    private String producer;
    @OneToMany(mappedBy = "productHistory",cascade = CascadeType.REMOVE)
    private List<HistoryDetails> historyDetails;
}
