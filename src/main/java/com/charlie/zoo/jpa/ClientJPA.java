package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientJPA extends JpaRepository<Client,Integer> {
}
