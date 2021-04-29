package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Phone;
import com.charlie.zoo.jpa.PhoneJPA;
import com.charlie.zoo.service.PhoneService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
public class PhoneServiceImpl implements PhoneService {
    private final PhoneJPA phoneJPA;

    @Override
    public Phone save(Phone phone) {
        Phone phoneDB = phoneJPA.findByPhone(phone.getPhone()).orElse(null);
        if(phoneDB!=null){
            return phoneDB;
        }
        return phoneJPA.save(phone);
    }

    @Override
    public Phone findById(int id) {
        return phoneJPA.findById(id).orElse(null);
    }

    @Override
    public Phone findByPhone(String phone) {
        return phoneJPA.findByPhone(phone).orElse(null);
    }

    @Override
    public List<Phone> findAll() {
        return phoneJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        phoneJPA.deleteById(id);
    }

    @Override
    public void deleteAll(Collection<Phone> col) {
        phoneJPA.deleteAll(col);
    }


}
