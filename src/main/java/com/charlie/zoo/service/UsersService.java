package com.charlie.zoo.service;

import com.charlie.zoo.entity.Users;

import java.util.List;

public interface UsersService {
    Users save(Users users);
    Users findById(int id);
    List<Users> findAll();
    void deleteByID(int id);
}
