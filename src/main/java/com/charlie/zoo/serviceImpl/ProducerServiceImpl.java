package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Image;
import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.jpa.ProducerJPA;
import com.charlie.zoo.service.ImageService;
import com.charlie.zoo.service.ProducerService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
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
        Producer producerDB = new Producer();
        if(producer.getId()!=0){
            producerDB = findById(producer.getId());
        }else{
            producerDB = producerJPA.save(producerDB);
        }
        if(multipartFile!=null && multipartFile.getSize()>0) {
            Image image = imageService.update(multipartFile, producer);
            producerDB = image.getProducer();
            producerDB.setImage(image);
        }
        producerDB.setCountryName(producer.getCountryName());
        producerDB.setName(producer.getName());
        return producerJPA.save(producerDB);
    }

    @Override
    public Producer findById(int id) {
        return producerJPA.findById(id).orElse(null);
    }

    @Override
    public Producer findByName(String name) {
        return producerJPA.findFirstByName(name);
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
