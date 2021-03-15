package com.charlie.zoo.entity;

import com.charlie.zoo.enums.StatusOfEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String nameOfClient;
    private String phone;
    private String description;

    @OneToMany(mappedBy = "orderInfo")
    private List<OrderDetails> orderDetails;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity;
}
