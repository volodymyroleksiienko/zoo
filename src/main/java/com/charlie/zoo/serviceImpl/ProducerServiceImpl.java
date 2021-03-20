package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.jpa.ProducerJPA;
import com.charlie.zoo.service.ProducerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProducerServiceImpl implements ProducerService {
    private ProducerJPA producerJPA;

    @Override
    public Producer save(Producer producer) {
        return producerJPA.save(producer);
    }

    @Override
    public Producer findById(int id) {
        return producerJPA.findById(id).orElse(null);
    }

    @Override
    public List<Producer> findAll() {
        return producerJPA.findAll();
    }

    @Override
    public List<Integer> getListOfId() {
        return null;
    }

    @Override
    public void deleteByID(int id) {
        producerJPA.deleteById(id);
    }
}
