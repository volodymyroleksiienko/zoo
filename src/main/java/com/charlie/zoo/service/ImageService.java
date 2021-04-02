package com.charlie.zoo.service;

import com.charlie.zoo.entity.Image;
import com.charlie.zoo.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    Image save(MultipartFile multipartFile);
    List<Image> save(List<MultipartFile> fileList, Product product);
    List<Image> update(List<MultipartFile> fileList, Product product);
    Image update(MultipartFile multipartFile, Image imageDB);
    Image findMainByProductId(int id);
    List<Image> findByProductIdAndMainFalse(int id);
    List<Image> findAllByProductId(int id);
    Image findById(int id);
    void deleteById(int id);
}
