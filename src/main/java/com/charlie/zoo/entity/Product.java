package com.charlie.zoo.entity;

import com.charlie.zoo.enums.StatusOfEntity;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private String shortDescription;



    @Lob
    @Type(type = "org.hibernate.type.ImageType")
    private byte[] img;
    private String imgType;
    private String imgName;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String details;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String dailyNorm;
    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String producerDetails;

    @ManyToOne
    private Producer producer;

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
    private List<PackageType> packageType;

    @ManyToMany
    @JoinTable(
            name = "product_item",
            joinColumns = @JoinColumn(name = "product", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "item",referencedColumnName = "id")
    )
    private List<CategoryItem> categoryItems;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category",referencedColumnName = "id")
    )
    private List<Category> categories;

    @Enumerated(EnumType.STRING)
    private StatusOfEntity statusOfEntity=StatusOfEntity.ACTIVE;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", img=" + Arrays.toString(img) +
                ", imgType='" + imgType + '\'' +
                ", imgName='" + imgName + '\'' +
                ", details='" + details + '\'' +
                ", dailyNorm='" + dailyNorm + '\'' +
                ", producerDetails='" + producerDetails + '\'' +
                ", producer=" + producer +
                ", statusOfEntity=" + statusOfEntity +
                '}';
    }
}
