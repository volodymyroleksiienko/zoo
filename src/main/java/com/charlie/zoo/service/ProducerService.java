package com.charlie.zoo.service;

import com.charlie.zoo.entity.Producer;

import java.util.List;

public interface ProducerService {
    Producer save(Producer producer);
    Producer findById(int id);
    List<Producer> findAll();
    void deleteByID(int id);
}
