package com.charlie.zoo.service;

import com.charlie.zoo.entity.Client;
import com.charlie.zoo.entity.OrderInfo;

import java.util.List;

public interface ClientService {
    Client save(Client client);
    Client save(Client client,String[] phone);
    Client update(Client client,String[] phone);
    Client validate(OrderInfo orderInfo);
    Client findById(int id);
    List<Client> findByNameOrPhone(String search);
    List<Client> findAll();
    void deleteByID(int id);
}
