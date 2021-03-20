package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Product;
import com.charlie.zoo.enums.StatusOfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Repository
public interface ProductJpa extends JpaRepository<Product,Integer> {
    Set<Product> findByCategoriesIdInAndStatusOfEntity(List<Integer> categoryId, StatusOfEntity statusOfentity);


    Set<Product> findByCategoriesIdInAndPackageTypePackSizeInAndProducerIdInAndStatusOfEntity(List<Integer> categoryId,List<BigDecimal> packId,List<Integer> producerId, StatusOfEntity statusOfentity);

    Set<Product> findByCategoryItemsIdInAndPackageTypeIdInAndProducerIdInAndStatusOfEntity(List<Integer> categoryId,List<Integer> packId,List<Integer> producerId, StatusOfEntity statusOfentity);


    Set<Product> findByPackageTypePackSizeInAndStatusOfEntity(List<BigDecimal> packSize, StatusOfEntity statusOfEntity);

}
