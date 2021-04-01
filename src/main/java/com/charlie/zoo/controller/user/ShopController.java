package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.Animal;
import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.Image;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.service.AnimalService;
import com.charlie.zoo.service.CategoryService;
import com.charlie.zoo.service.ImageService;
import com.charlie.zoo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {
    private final ProductService productService;
    private final AnimalService animalService;
    private final CategoryService categoryService;
    private final ImageService imageService;

    @GetMapping("/{animalUrl}")
    public String getByAnimal(@PathVariable String animalUrl, Model model, String sortType, Double maxPrice, Double minPrice, Integer producerId ){
        Animal animal = animalService.findByUrl(animalUrl);
        if(animal!=null) {
            model.addAttribute("products", productService.findByAnimal(animal));
            model.addAttribute("categoryBtn", animal.getCategories());
        }
        return "user/shop";
    }

    @GetMapping("/{animalUrl}/{categoryUrl}")
    public String getByAnimalByCategory(@PathVariable String animalUrl, @PathVariable String categoryUrl,Model model){
        Category category = categoryService.findByUrl(categoryUrl);
        if(category!=null) {
            model.addAttribute("products", productService.findByAnimalByCategory(category));
            model.addAttribute("categoryBtn", category.getCategoryItems());
        }
        return "user/shop";
    }

//    @GetMapping("/${animalUrl}/${categoryUrl}/${subCategoryUrl}")



    @GetMapping("/getImgByProductId/{productId}")
    public ResponseEntity<ByteArrayResource> getImgByProductId(@PathVariable int productId){
        Image doc = imageService.findMainByProductId(productId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getImgType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getImgName()+"\"")
                .body(new ByteArrayResource(doc.getImg()));
    }

    @GetMapping("/getAnimalImg/{animalId}")
    public ResponseEntity<ByteArrayResource> getAnimalImg(@PathVariable int animalId){
        Animal doc = animalService.findById(animalId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getImgType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getImgName()+"\"")
                .body(new ByteArrayResource(doc.getImg()));
    }
}
