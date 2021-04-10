package com.charlie.zoo.entity.dto;

import com.charlie.zoo.entity.CategoryItem;
import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.enums.StatusOfEntity;
import com.charlie.zoo.service.AnimalService;
import com.charlie.zoo.service.ProducerService;
import com.charlie.zoo.utils.SpringContext;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class ProductDto {
    private int id;
    private String name;
    private String shortDescription;
    private String details;
    private String dailyNorm;
    private String producerDetails;
    private int producer;

    public static Product convertToProduct(String json){
        ProductDto productDto = new Gson().fromJson(json,ProductDto.class);
        return convertToProduct(productDto);
    }


    public static Product convertToProduct(ProductDto productDto){
        Product product = new Product();
        product.setId(productDto.id);
        product.setName(productDto.name);
        product.setShortDescription(productDto.shortDescription);
        product.setDetails(productDto.details);
        product.setDailyNorm(productDto.dailyNorm);
        product.setProducerDetails(productDto.producerDetails);
        ProducerService producerService = SpringContext.getBean(ProducerService.class);
        product.setProducer(producerService.findById(productDto.producer));
        return product;
    }

    public static ProductDto convertToDto(Product product){
        ProductDto dto =  new ProductDto();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setShortDescription(product.getShortDescription());
        dto.setDetails(product.getDetails());
        dto.setDailyNorm(product.getDailyNorm());
        dto.setProducerDetails(product.getProducerDetails());
        return dto;
    }
    public static List<ProductDto> convertToDto(List<Product> product){
        List<ProductDto> dto =  new ArrayList<>();
        for(Product pr:product){
            dto.add(convertToDto(pr));
        }
        return dto;
    }

}
