package com.charlie.zoo.entity;

import com.charlie.zoo.enums.StatusOfEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Category{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int position;
    private String name;
    @Column(unique = false)
    private String url;

    @OneToMany(mappedBy = "category")
    private List<CategoryItem> categoryItems;

    @ManyToOne
    private Animal animal;

    @ManyToMany(mappedBy = "categories")
    private List<Product> products;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;

}
