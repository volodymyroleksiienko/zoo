package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryJPA extends JpaRepository<Category,Integer> {
    List<Category> findByAnimalIdOrderByPositionAsc(int id);

    @Query("select c.id from Category  c")
    List<Integer> getListOfId();

    Category findFirstByAnimalUrlAndUrl(String animalUrl,String url);
}
