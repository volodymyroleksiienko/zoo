package com.charlie.zoo.service;

import com.charlie.zoo.entity.Animal;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnimalService {
    Animal save(Animal animal);
    Animal save(Animal animal, MultipartFile multipartFile);
    Animal findById(int id);
    Animal findByUrl(String url);
    List<Animal> findAll();
    void deleteByID(int id);
}
