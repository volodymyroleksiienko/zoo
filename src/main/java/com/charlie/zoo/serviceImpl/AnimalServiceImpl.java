package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Animal;
import com.charlie.zoo.jpa.AnimalJPA;
import com.charlie.zoo.service.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AnimalServiceImpl implements AnimalService  {
    private AnimalJPA animalJPA;
    @Override
    public Animal save(Animal animal) {
        return animalJPA.save(animal);
    }

    @Override
    public Animal findById(int id) {
        return animalJPA.findById(id).orElse(null);
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
