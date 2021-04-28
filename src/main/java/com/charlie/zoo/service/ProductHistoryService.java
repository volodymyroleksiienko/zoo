package com.charlie.zoo.service;

import com.charlie.zoo.entity.ProductHistory;

import java.util.List;

public interface ProductHistoryService {
    ProductHistory save(ProductHistory productHistory);
    ProductHistory update(ProductHistory productHistory);
    ProductHistory countSummaryPrice(int id);
    ProductHistory findById(int id);
    List<ProductHistory> findAll();
    void deleteByID(int id);
}
