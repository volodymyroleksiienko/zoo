package com.charlie.zoo.entity;

import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.enums.StatusOfOrder;
import com.charlie.zoo.enums.StatusOfPayment;
import lombok.Data;

import javax.persistence.*;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class OrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @GeneratedValue(strategy = GenerationType.AUTO)
    private int index;

    private String date;
    private String nameOfClient;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Phone phone;
    private String description;
    private double sumPrice;

    private boolean lvivDelivering;
    private boolean novaPoshtaDelivering;
    private boolean payByCard;
    private boolean payByCash;
    private boolean opt;


    @OneToMany(mappedBy = "orderInfo",fetch = FetchType.EAGER)
    private List<OrderDetails> orderDetails;

    @ManyToOne
    private Users createdBy;

    @Enumerated(EnumType.STRING)
    private StatusOfPayment payment = StatusOfPayment.NOT_SUBMITTED;

    @Enumerated(EnumType.STRING)
    private StatusOfOrder statusOfOrder = StatusOfOrder.CART;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity = StatusOfEntity.ACTIVE;

    @Override
    public String toString() {
        return "OrderInfo{" +
                "id=" + id +
                ", date='" + date + '\'' +
                ", nameOfClient='" + nameOfClient + '\'' +
                ", phone=" + phone +
                ", description='" + description + '\'' +
                ", sumPrice=" + sumPrice +
                ", lvivDelivering=" + lvivDelivering +
                ", novaPoshtaDelivering=" + novaPoshtaDelivering +
                ", payByCard=" + payByCard +
                ", payByCash=" + payByCash +
                ", opt=" + opt +
                ", payment=" + payment +
                ", statusOfOrder=" + statusOfOrder +
                ", statusOfEntity=" + statusOfEntity +
                '}';
    }
}
