package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.Product;
import com.charlie.zoo.entity.dto.ProductDto;
import com.charlie.zoo.service.ProductService;
import com.charlie.zoo.utils.haustier.HaustierXmlParser;
import com.charlie.zoo.utils.haustier.Rss;
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
    private final HaustierXmlParser haustierXmlParser;

    @ResponseBody
    @PostMapping("/search")
    public List<ProductDto> searchByNameOfProduct(String name){
            return ProductDto.convertToDto(productService.find15ByName(name));
        }

    @ResponseBody
    @PostMapping("/parse")
    public void parse(MultipartFile file){
        Rss rss = haustierXmlParser.parse(file);
        List<Product> productList = haustierXmlParser.convertToProduct(rss);
        productService.saveAll(productList);
     }

}
