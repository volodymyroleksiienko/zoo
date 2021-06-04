package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Client;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.enums.StatusOfEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientJPA extends JpaRepository<Client,Integer> {
    List<Client> findByNameContainingIgnoreCase(String search);
}
