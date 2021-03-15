package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProducerJPA extends JpaRepository<Producer, Integer> {
}
