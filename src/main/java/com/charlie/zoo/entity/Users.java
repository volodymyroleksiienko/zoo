package com.charlie.zoo.entity;

import com.charlie.zoo.enums.UserRole;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
public class Users{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(mappedBy = "createdBy")
    private List<OrderInfo> orderInfoList;
}
