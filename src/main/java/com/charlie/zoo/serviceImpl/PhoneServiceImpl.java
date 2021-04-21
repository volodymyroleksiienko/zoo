package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Phone;
import com.charlie.zoo.jpa.PhoneJPA;
import com.charlie.zoo.service.PhoneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PhoneServiceImpl implements PhoneService {
    private PhoneJPA phoneJPA;

    @Override
    public Phone save(Phone phone) {
        return phoneJPA.save(phone);
    }

    @Override
    public Phone findById(int id) {
        return phoneJPA.findById(id).orElse(null);
    }

    @Override
    public List<Phone> findAll() {
        return phoneJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        phoneJPA.deleteById(id);
    }
}
