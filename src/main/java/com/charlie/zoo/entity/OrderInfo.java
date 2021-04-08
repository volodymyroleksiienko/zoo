package com.charlie.zoo.entity;

import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.enums.StatusOfOrder;
import com.charlie.zoo.enums.StatusOfPayment;
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

    private String date;
    private String nameOfClient;
    private String phone;
    private String description;
    private double sumPrice;

    private boolean lvivDelivering;
    private boolean novaPoshtaDelivering;
    private boolean payByCard;
    private boolean payByCash;


    @OneToMany(mappedBy = "orderInfo",fetch = FetchType.EAGER)
    private List<OrderDetails> orderDetails;

    @Enumerated(EnumType.STRING)
    private StatusOfPayment payment = StatusOfPayment.NOT_SUBMITTED;

    @Enumerated(EnumType.STRING)
    private StatusOfOrder statusOfOrder = StatusOfOrder.NEW;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;
}
