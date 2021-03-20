package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.PackageType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PackageTypeJPA extends JpaRepository<PackageType,Integer> {
    void deleteAllByProductId(int productId);

    @Query("select pt.packSize from PackageType pt")
    List<BigDecimal> getListOfSizes();
}
