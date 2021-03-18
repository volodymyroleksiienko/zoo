package com.charlie.zoo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String countryName;

    @OneToMany(mappedBy = "producer")
    private List<Product> products;

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
