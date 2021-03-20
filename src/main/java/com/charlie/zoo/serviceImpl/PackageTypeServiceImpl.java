package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.jpa.PackageTypeJPA;
import com.charlie.zoo.service.PackageTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class PackageTypeServiceImpl implements PackageTypeService {
    private final PackageTypeJPA packageTypeJPA;

    @Override
    public PackageType save(PackageType packageType) {
        return packageTypeJPA.save(packageType);
    }

    @Override
    public PackageType findById(int id) {
        return packageTypeJPA.findById(id).orElse(null);
    }

    @Override
    public List<PackageType> findAll() {
        return packageTypeJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        packageTypeJPA.deleteById(id);
    }

    @Override
    public List<BigDecimal> getListOfSizes() {
        return packageTypeJPA.getListOfSizes();
    }

    @Override
    @Transactional
    public void deleteAllByProductId(int productId) {
        packageTypeJPA.deleteAllByProductId(productId);
    }
}
