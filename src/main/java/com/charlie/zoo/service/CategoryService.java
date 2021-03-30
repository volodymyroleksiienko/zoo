package com.charlie.zoo.service;

import com.charlie.zoo.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);
    Category findById(int id);
    Category findByUrl(String url);
    List<Category> findByAnimalId(int id);
    List<Category> findAll();
    List<Integer> getListOfId();
    void deleteByID(int id);
}
