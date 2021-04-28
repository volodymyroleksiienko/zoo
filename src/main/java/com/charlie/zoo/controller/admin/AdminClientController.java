package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.Client;
import com.charlie.zoo.service.ClientService;
import com.charlie.zoo.service.PhoneService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/clients")
public class AdminClientController {
    private final ClientService clientService;
    private final PhoneService phoneService;

    public AdminClientController(ClientService clientService, PhoneService phoneService) {
        this.clientService = clientService;
        this.phoneService = phoneService;
    }

    @GetMapping
    public String getClient(Model model){
        model.addAttribute("clients",clientService.findAll());
        return "admin/clients";
    }

    @PostMapping("/add")
    public String addClient(Client client, String[] phone){
        clientService.save(client,phone);
        return "redirect:/admin/clients";
    }

    @PostMapping("/edit")
    public String editClient(Client client, String[] phone){
        clientService.update(client,phone);
        return "redirect:/admin/clients";
    }

    @PostMapping("/delete")
    public String deleteClient(int id){
        clientService.deleteByID(id);
        return "redirect:/admin/clients";
    }
}
