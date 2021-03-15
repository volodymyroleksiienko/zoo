package com.charlie.zoo.service;

import com.charlie.zoo.entity.CategoryItem;

import java.util.List;

public interface CategoryItemService {
    CategoryItem save(CategoryItem categoryItem);
    CategoryItem findById(int id);
    List<CategoryItem> findAll();
    void deleteByID(int id);
}
