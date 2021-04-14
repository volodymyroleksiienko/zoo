package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Producer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProducerJPA extends JpaRepository<Producer, Integer> {
    @Query("select p.id from Producer p ")
    List<Integer> getListOfId();
}
