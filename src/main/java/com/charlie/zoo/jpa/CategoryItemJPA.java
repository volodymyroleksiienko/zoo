package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.CategoryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Calendar;
import java.util.List;

@Repository
public interface CategoryItemJPA extends JpaRepository<CategoryItem,Integer> {
    List<CategoryItem> findByCategoryIdOrderByPositionDesc(int id);
    List<CategoryItem> findByCategoryAnimalId(int animalId);
    @Query("select c from CategoryItem  c where c.category.animal.url like ?1 and c.category.url like ?2 and c.url like ?3")
    CategoryItem findFirstByAnimalUrlAndUrl(String animalUrl, String categoryUrl, String itemUrl);
    @Query("select c.id from CategoryItem  c")
    List<Integer> getListOfId();
}
