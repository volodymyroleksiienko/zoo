package com.charlie.zoo.service;

import com.charlie.zoo.entity.PackageType;

import java.util.List;

public interface PackageTypeService {
    PackageType save(PackageType packageType);
    PackageType findById(int id);
    List<PackageType> findAll();
    void deleteByID(int id);
    void deleteAllByProductId(int productId);
}
