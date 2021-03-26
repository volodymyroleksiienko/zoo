package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Animal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimalJPA extends JpaRepository<Animal,Integer> {
    Animal findByUrl(String url);
}
