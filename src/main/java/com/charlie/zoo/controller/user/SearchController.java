package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.dto.ProductDto;
import com.charlie.zoo.service.ProductService;
import com.charlie.zoo.utils.haustier.HaustierXmlParser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

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

    @ResponseBody
    @PostMapping("/parse")
    public void parse(MultipartFile file){
         HaustierXmlParser.parse(file);
     }

}
