package com.charlie.zoo.service;

import com.charlie.zoo.entity.Producer;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProducerService {
    Producer save(MultipartFile multipartFile,Producer producer);
    Producer findById(int id);
    Producer findByName(String name);
    List<Producer> findAll();
    List<Integer> getListOfId();
    void deleteByID(int id);
}
