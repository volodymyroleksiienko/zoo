package com.charlie.zoo.entity;

import lombok.Data;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String phone;

    @ManyToOne
    private Client client;

    @OneToMany(mappedBy = "phone")
    private List<OrderInfo> orderInfo;

    @Override
    public String toString() {
        return "Phone{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", client=" + client +
                '}';
    }
}
