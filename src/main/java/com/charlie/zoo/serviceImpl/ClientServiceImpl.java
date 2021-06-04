package com.charlie.zoo.serviceImpl;

import com.charlie.zoo.entity.Client;
import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.Phone;
import com.charlie.zoo.enums.ClientRoles;
import com.charlie.zoo.jpa.ClientJPA;
import com.charlie.zoo.service.ClientService;
import com.charlie.zoo.service.PhoneService;
import org.springframework.stereotype.Service;

import java.util.*;
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
        client = save(client);
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
    public Client validate(String name,String phone) {
        phone = phone.replaceAll("\\D+","");
        Phone phoneDB = phoneService.findByPhone(phone);
        if(phoneDB!=null){
            return phoneDB.getClient();
        }else{
            Client client = new Client();
            client.setName(name);
            client.setRole(ClientRoles.RETAILER);
            client = save(client);
            Phone phoneNew = new Phone();
            phoneNew.setClient(client);
            phoneNew.setPhone(phone);
            phoneService.save(phoneNew);
            return client;
        }
    }

    @Override
    public Client findById(int id) {
        return clientJPA.findById(id).orElse(null);
    }

    @Override
    public List<Client> findByNameOrPhone(String search) {
        Set<Client> set = new HashSet<>();
        for(Phone phone: phoneService.findByPhoneContaining(search)) {
            if(phone.getClient()!=null) {
                set.add(phone.getClient());
            }
        }
        set.addAll(clientJPA.findByNameContainingIgnoreCase(search));
        return new ArrayList<>(set);
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
