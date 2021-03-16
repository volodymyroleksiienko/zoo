package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryJPA extends JpaRepository<Category,Integer> {
    List<Category> findByAnimalIdOrderByPositionAsc(int id);
}
