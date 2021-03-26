package com.charlie.zoo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Calendar;
import java.util.List;

@Entity
@Data
public class CategoryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private int position;
    private String name;
    @Column(unique = true)
    private String url;

    @ManyToOne
    private Category category;

    @ManyToMany(mappedBy = "categoryItems")
    private List<Product> products;
}
