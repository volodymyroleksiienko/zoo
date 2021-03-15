package com.charlie.zoo.service;

import com.charlie.zoo.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category category);
    Category findById(int id);
    List<Category> findAll();
    void deleteByID(int id);
}
