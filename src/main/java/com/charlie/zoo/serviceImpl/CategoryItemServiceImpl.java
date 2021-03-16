package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.CategoryItem;
import com.charlie.zoo.jpa.CategoryItemJPA;
import com.charlie.zoo.service.CategoryItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryItemServiceImpl implements CategoryItemService {
    private final CategoryItemJPA categoryItemJPA;

    @Override
    public CategoryItem save(CategoryItem categoryItem) {
        return categoryItemJPA.save(categoryItem);
    }

    @Override
    public CategoryItem findById(int id) {
        return categoryItemJPA.findById(id).orElse(null);
    }

    @Override
    public List<CategoryItem> findByCategoryAnimalId(int animalId) {
        return categoryItemJPA.findByCategoryAnimalId(animalId);
    }

    @Override
    public List<CategoryItem> findByCategoryId(int id) {
        return categoryItemJPA.findByCategoryIdOrderByPositionDesc(id);
    }

    @Override
    public List<CategoryItem> findAll() {
        return categoryItemJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        categoryItemJPA.deleteById(id);
    }
}
