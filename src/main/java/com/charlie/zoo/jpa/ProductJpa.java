package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.enums.StatusOfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Repository
public interface ProductJpa extends JpaRepository<Product,Integer> {
    TreeSet<Product> findByCategoriesIdInAndStatusOfEntity(List<Integer> categoryId, StatusOfEntity statusOfentity);
    TreeSet<Product> findByCategoryItemsIdInAndStatusOfEntity(List<Integer> categoryId, StatusOfEntity statusOfentity);

    List<Product> findFirst15ByNameContainingIgnoreCaseOrShortDescriptionContainingIgnoreCaseAndStatusOfEntity(String name,String desc,StatusOfEntity status);

    TreeSet<Product> findByCategoriesIdInAndCategoryItemsIdInAndPackageTypePackSizeInAndProducerIdInAndStatusOfEntity(List<Integer> categoryId,List<Integer> categoryItem,List<BigDecimal> packId,List<Integer> producerId, StatusOfEntity statusOfentity);



    List<Product> findByStatusOfEntity(StatusOfEntity status);


    TreeSet<Product> findByCategoriesIn(List<Category> category);
    TreeSet<Product> findByCategoriesInAndStatusOfEntity(List<Category> category,StatusOfEntity status);

   }
