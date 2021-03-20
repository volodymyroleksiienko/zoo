package com.charlie.zoo.service;

import com.charlie.zoo.entity.PackageType;

import java.math.BigDecimal;
import java.util.List;

public interface PackageTypeService {
    PackageType save(PackageType packageType);
    PackageType findById(int id);
    List<PackageType> findAll();
    void deleteByID(int id);
    List<BigDecimal> getListOfSizes();
    void deleteAllByProductId(int productId);
}
