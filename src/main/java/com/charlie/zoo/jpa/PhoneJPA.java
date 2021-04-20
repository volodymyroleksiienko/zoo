package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Phone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhoneJPA extends JpaRepository<Phone,Integer> {
}
