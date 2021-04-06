package com.charlie.zoo.entity;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

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

    @OneToOne
    private Producer producer;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id &&
                main == image.main &&
                Arrays.equals(img, image.img) &&
                Objects.equals(imgType, image.imgType) &&
                Objects.equals(imgName, image.imgName);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, imgType, imgName, main);
        result = 31 * result + Arrays.hashCode(img);
        return result;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", imgType='" + imgType + '\'' +
                ", imgName='" + imgName + '\'' +
                ", main=" + main +
                '}';
    }
}
