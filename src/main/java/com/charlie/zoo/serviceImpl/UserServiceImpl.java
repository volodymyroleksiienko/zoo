package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Users;
import com.charlie.zoo.jpa.UsersJPA;
import com.charlie.zoo.service.UsersService;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UsersService {
    private final UsersJPA usersJPA;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Users save(Users users) {
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        return usersJPA.save(users);
    }

    @Override
    public Users getAuth(Authentication authentication) {
        String username =  authentication.getName();
        return findByUsername(username);
    }

    @Override
    public Users update(Users users) {
        Users userDB =  findById(users.getId());
        if(userDB!=null){
            if(!users.getPassword().equals(userDB.getPassword())){
                userDB.setPassword(passwordEncoder.encode(users.getPassword()));
            }
            userDB.setName(users.getName());
            userDB.setRole(users.getRole());
            userDB.setUsername(users.getUsername());
            return usersJPA.save(userDB);
        }
        return null;
    }

    @Override
    public Users findById(int id) {
        return usersJPA.findById(id).orElse(null);
    }

    @Override
    public List<Users> findById(Integer[] id) {
        List<Users> users = new ArrayList<>();
        for(Integer i:id){
            users.add(findById(i));
        }
        return users;
    }

    @Override
    public Users findByUsername(String username) {
        return usersJPA.findByUsername(username);
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
