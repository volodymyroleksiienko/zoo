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
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/shop")
@AllArgsConstructor
public class ShopController {
    private final AnimalService animalService;
    private final ImageService imageService;



    @GetMapping("/getImgByProductId/{productId}")
    public ResponseEntity<ByteArrayResource> getImgByProductId(@PathVariable int productId){
        Image doc = imageService.findMainByProductId(productId);
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getImgType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getImgName()+"\"")
                .body(new ByteArrayResource(doc.getImg()));
    }

    @GetMapping("/getImg/{imgId}")
    public ResponseEntity<ByteArrayResource> getImg(@PathVariable int imgId){
        Image doc = imageService.findById(imgId);
        if(doc==null || doc.getImgName()==null) return null;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getImgType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getImgName()+"\"")
                .body(new ByteArrayResource(doc.getImg()));
    }

    @ResponseBody
    @PostMapping("/deleteImg/{imgId}")
    public void deleteImg(@PathVariable int imgId){
        imageService.deleteById(imgId);
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
