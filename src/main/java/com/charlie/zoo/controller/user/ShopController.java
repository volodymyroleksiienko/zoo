package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.Animal;
import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.Image;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.service.AnimalService;
import com.charlie.zoo.service.CategoryService;
import com.charlie.zoo.service.ImageService;
import com.charlie.zoo.service.ProductService;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import lombok.AllArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
    public ResponseEntity<ByteArrayResource> getImg(@PathVariable int imgId) throws IOException {
        Image doc = imageService.findById(imgId);
        if(doc==null || doc.getImgName()==null) return null;
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(doc.getImgType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+doc.getImgName()+"\"")
                .body(new ByteArrayResource(compressImage(doc.getImg())));
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

    public byte[] compressImage(byte[] bytes) throws IOException {
        BufferedImage image = Thumbnails.of(new ByteArrayInputStream(bytes))
                .height(250)
                .width(250)
                .outputQuality(0.5)
                .asBufferedImage();
        return toByteArray(image,"png");
    }

    public static byte[] toByteArray(BufferedImage bi, String format)
            throws IOException {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, format, baos);
        return baos.toByteArray();

    }
}
