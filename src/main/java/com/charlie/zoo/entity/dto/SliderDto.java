package com.charlie.zoo.entity.dto;

import com.charlie.zoo.entity.Product;
import lombok.Data;

import java.util.Set;

@Data
public class SliderDto {
    private String animal;
    private Set<Product> products;
}
