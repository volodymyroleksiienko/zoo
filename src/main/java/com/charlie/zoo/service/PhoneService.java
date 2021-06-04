package com.charlie.zoo.service;

import com.charlie.zoo.entity.Phone;

import java.util.Collection;
import java.util.List;

public interface PhoneService {
    Phone save(Phone phone);
    Phone findById(int id);
    Phone findByPhone(String phone);
    List<Phone> findByPhoneContaining(String search);
    List<Phone> findAll();
    void deleteByID(int id);
    void deleteAll(Collection<Phone> col);
}
