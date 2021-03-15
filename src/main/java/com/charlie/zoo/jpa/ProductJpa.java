package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductJpa extends JpaRepository<Product,Integer> {
}
