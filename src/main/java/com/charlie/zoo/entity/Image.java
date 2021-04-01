package com.charlie.zoo.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Data
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] img;
    private String imgType;
    private String imgName;

    private boolean main=false;

    @ManyToOne
    private Product product;
}
