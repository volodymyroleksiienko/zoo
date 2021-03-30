package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.enums.StatusOfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductJpa extends JpaRepository<Product,Integer> {
    Set<Product> findByCategoriesIdInAndStatusOfEntity(List<Integer> categoryId, StatusOfEntity statusOfentity);


    Set<Product> findByCategoriesIdInAndCategoryItemsIdInAndPackageTypePackSizeInAndProducerIdInAndStatusOfEntity(List<Integer> categoryId,List<Integer> categoryItem,List<BigDecimal> packId,List<Integer> producerId, StatusOfEntity statusOfentity);

    Set<Product> findByCategoriesIn(List<Category> category);
}
