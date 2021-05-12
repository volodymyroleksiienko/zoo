package com.charlie.zoo.service;

import com.charlie.zoo.entity.Users;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UsersService {
    Users save(Users users);
    Users getAuth(Authentication authentication);
    Users update(Users users);
    Users findById(int id);
    List<Users> findById(Integer[] id);
    Users findByUsername(String username);
    List<Users> findAll();
    void deleteByID(int id);
}
