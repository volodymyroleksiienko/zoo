package com.charlie.zoo.utils.haustier;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="item")
@Data
public class Item {

    @XmlElement(name = "id", namespace = "http://base.google.com/ns/1.0")
    private String id;

    @XmlElement(name = "price", namespace = "http://base.google.com/ns/1.0")
    private String price;
    @XmlElement(name = "sale_price", namespace = "http://base.google.com/ns/1.0")
    private String salePrice;

    @XmlElement(name = "description")
    private String description;

    @XmlElement(name = "title")
    private String title;
    @XmlElement(name = "image_link", namespace = "http://base.google.com/ns/1.0")
    private String imageLink;

    @XmlElement(name = "additional_image_link", namespace = "http://base.google.com/ns/1.0")
    private List<String> addImageLink;

}