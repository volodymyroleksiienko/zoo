package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Users;
import com.charlie.zoo.jpa.UsersJPA;
import com.charlie.zoo.service.UsersService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UsersService {
    private final UsersJPA usersJPA;

    @Override
    public Users save(Users users) {
        return usersJPA.save(users);
    }

    @Override
    public Users findById(int id) {
        return usersJPA.findById(id).orElse(null);
    }

    @Override
    public List<Users> findAll() {
        return usersJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        usersJPA.deleteById(id);
    }
}
