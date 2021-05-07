package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Animal;
import com.charlie.zoo.jpa.AnimalJPA;
import com.charlie.zoo.service.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService  {
    private final AnimalJPA animalJPA;
    @Override
    public Animal save(Animal animal) {
        return animalJPA.save(animal);
    }

    @Override
    public Animal save(Animal animal, MultipartFile multipartFile) {
        if(multipartFile!=null && multipartFile.getSize()>0){
            System.out.println("size "+multipartFile.getSize());
            try {
                animal.setImg(multipartFile.getBytes());
                animal.setImgName(multipartFile.getOriginalFilename());
                animal.setImgType(multipartFile.getContentType());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        return save(animal);
    }

    @Override
    public Animal update(Animal animal, MultipartFile multipartFile) {
        Animal animalDB = findById(animal.getId());
        if(multipartFile!=null && multipartFile.getSize()>0){
            try {
                animalDB.setImg(multipartFile.getBytes());
                animalDB.setImgName(multipartFile.getOriginalFilename());
                animalDB.setImgType(multipartFile.getContentType());
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
        animalDB.setDescription(animal.getDescription());
        animalDB.setName(animal.getName());
        animalDB.setUrl(animal.getUrl());
        return save(animalDB);
    }

    @Override
    public Animal findById(int id) {
        return animalJPA.findById(id).orElse(null);
    }

    @Override
    public Animal findByUrl(String url) {
        return animalJPA.findByUrl(url);
    }

    @Override
    public List<Animal> findAll() {
        return animalJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        animalJPA.deleteById(id);
    }
}
