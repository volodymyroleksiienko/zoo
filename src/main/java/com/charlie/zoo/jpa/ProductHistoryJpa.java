package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductHistoryJpa extends JpaRepository<ProductHistory,Integer> {
}
