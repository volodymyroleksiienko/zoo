package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Client;
import com.charlie.zoo.entity.Phone;
import com.charlie.zoo.jpa.ClientJPA;
import com.charlie.zoo.service.ClientService;
import com.charlie.zoo.service.PhoneService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientJPA clientJPA;
    private final PhoneService phoneService;

    public ClientServiceImpl(ClientJPA clientJPA, PhoneService phoneService) {
        this.clientJPA = clientJPA;
        this.phoneService = phoneService;
    }

    @Override
    public Client save(Client client) {
        return clientJPA.save(client);
    }

    @Override
    public Client save(Client client, String[] phone) {
        List<Phone> phoneList = new ArrayList<>();
        for (String number : phone) {
            if (!number.isEmpty()) {
                Phone ph = new Phone();
                ph.setPhone(number);
                ph.setClient(client);
                phoneList.add(phoneService.save(ph));
            }
        }
        client.setPhones(phoneList);
        return save(client);
    }

    @Override
    public Client update(Client client, String[] phone) {
        Client clientDB = findById(client.getId());
        if(clientDB!=null){
            System.out.println(clientDB);
            List<Integer> phoneList = clientDB.getPhones().stream().map(Phone::getId).collect(Collectors.toList());
            for (Integer id:phoneList){
                phoneService.deleteByID(id);
            }
        }
        return save(client,phone);
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
