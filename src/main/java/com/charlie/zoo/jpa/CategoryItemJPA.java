package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryItemJPA extends JpaRepository<CategoryItem,Integer> {
    List<CategoryItem> findByCategoryId(int id);
}
