package com.charlie.zoo.service;

import com.charlie.zoo.entity.CategoryItem;

import java.util.List;

public interface CategoryItemService {
    CategoryItem save(CategoryItem categoryItem);
    CategoryItem findById(int id);
    CategoryItem findByUrl(String animalUrl,String categoryUrl, String subCategoryUrl);
    List<CategoryItem> findByCategoryAnimalId(int animalId);
    List<CategoryItem> findByCategoryId(int id);
    List<CategoryItem> findAll();
    List<Integer> getListOfId();
    void deleteByID(int id);
}
