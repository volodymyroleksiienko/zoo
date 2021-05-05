package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductHistoryJpa extends JpaRepository<ProductHistory,Integer> {
    List<ProductHistory> findByDateBetween(String from, String to);
    List<ProductHistory> findByDateBefore(String from);
}
