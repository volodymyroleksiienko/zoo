package com.charlie.zoo.service;

import com.charlie.zoo.entity.PackageType;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.List;

public interface PackageTypeService {
    PackageType save(PackageType packageType);
    PackageType findById(int id);
    List<PackageType> findAll();
    List<PackageType> findFirst2ByProductNameContaining(String name);
    void deleteByID(int id);
    List<BigDecimal> getListOfSizes();
    void deleteAllByProductId(int productId);
}
