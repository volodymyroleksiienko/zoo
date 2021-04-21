package com.charlie.zoo.service;

import com.charlie.zoo.entity.Phone;

import java.util.List;

public interface PhoneService {
    Phone save(Phone phone);
    Phone findById(int id);
    List<Phone> findAll();
    void deleteByID(int id);
}
