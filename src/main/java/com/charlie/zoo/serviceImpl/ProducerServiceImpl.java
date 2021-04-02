package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.jpa.ProducerJPA;
import com.charlie.zoo.service.ImageService;
import com.charlie.zoo.service.ProducerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@AllArgsConstructor
public class ProducerServiceImpl implements ProducerService {
    private final ProducerJPA producerJPA;
    private final ImageService imageService;

    @Override
    public Producer save(MultipartFile multipartFile,Producer producer) {
        producer.setImage(imageService.update(multipartFile,producer));
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
        return producerJPA.getListOfId();
    }

    @Override
    public void deleteByID(int id) {
        producerJPA.deleteById(id);
    }
}
