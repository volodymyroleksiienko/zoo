package com.charlie.zoo.service;

import com.charlie.zoo.entity.Client;

import java.util.List;

public interface ClientService {
    Client save(Client client);
    Client findById(int id);
    List<Client> findAll();
    void deleteByID(int id);
}
