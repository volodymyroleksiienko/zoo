package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.dto.ProductDto;
import com.charlie.zoo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class SearchController {
    private final ProductService productService;

    @ResponseBody
    @PostMapping("/search")
    public List<ProductDto> searchByNameOfProduct(String name){
            return ProductDto.convertToDto(productService.find15ByName(name));
        }

}
