package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Client;
import com.charlie.zoo.jpa.ClientJPA;
import com.charlie.zoo.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientJPA clientJPA;

    @Override
    public Client save(Client client) {
        return clientJPA.save(client);
    }

    @Override
    public Client findById(int id) {
        return clientJPA.findById(id).orElse(null);
    }

    @Override
    public List<Client> findAll() {
        return clientJPA.findAll();
    }

    @Override
    public void deleteByID(int id) {
        clientJPA.deleteById(id);
    }
}
