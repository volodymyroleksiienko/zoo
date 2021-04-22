package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.ProductHistory;
import com.charlie.zoo.jpa.ProductHistoryJpa;
import com.charlie.zoo.service.ProductHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductHistoryServiceImpl implements ProductHistoryService {
    private final ProductHistoryJpa productHistoryJpa;

    @Override
    public ProductHistory save(ProductHistory productHistory) {
        return productHistoryJpa.save(productHistory);
    }

    @Override
    public ProductHistory findById(int id) {
        return productHistoryJpa.findById(id).orElse(null);
    }

    @Override
    public List<ProductHistory> findAll() {
        return productHistoryJpa.findAll();
    }

    @Override
    public void deleteByID(int id) {
        productHistoryJpa.deleteById(id);
    }
}
