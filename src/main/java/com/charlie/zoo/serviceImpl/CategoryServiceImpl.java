package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Category;
import com.charlie.zoo.jpa.CategoryJPA;
import com.charlie.zoo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryJPA categoryJPA;
    @Override
    public Category save(Category category) {
        return categoryJPA.save(category);
    }

    @Override
    public Category findById(int id) {
        return categoryJPA.findById(id).orElse(null);
    }

    @Override
    public List<Category> findByAnimalId(int id) {
        return categoryJPA.findByAnimalIdOrderByPositionAsc(id);
    }

    @Override
    public List<Category> findAll() {
        return categoryJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        categoryJPA.deleteById(id);
    }
}
