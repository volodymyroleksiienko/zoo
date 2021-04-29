package com.charlie.zoo.jpa;

import com.charlie.zoo.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersJPA extends JpaRepository<Users,Integer> {
    Users findByUsername(String username);
}
