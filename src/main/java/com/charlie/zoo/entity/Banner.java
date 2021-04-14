package com.charlie.zoo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String url;
    @OneToOne
    private Image image;
}
