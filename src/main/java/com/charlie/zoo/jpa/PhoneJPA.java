package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhoneJPA extends JpaRepository<Phone,Integer> {
    Optional<Phone> findByPhone(String phone);
}
