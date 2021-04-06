package com.charlie.zoo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Data
public class Producer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String countryName;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    private Image image;

    @OneToMany(mappedBy = "producer")
    private List<Product> products;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Producer producer = (Producer) o;
        return id == producer.id &&
                Objects.equals(name, producer.name) &&
                Objects.equals(countryName, producer.countryName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, countryName);
    }

    @Override
    public String toString() {
        return "Producer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
