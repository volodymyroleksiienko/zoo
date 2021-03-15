package com.charlie.zoo.service;

import com.charlie.zoo.entity.Animal;

import java.util.List;

public interface AnimalService {
    Animal save(Animal animal);
    Animal findById(int id);
    List<Animal> findAll();
    void deleteByID(int id);
}
