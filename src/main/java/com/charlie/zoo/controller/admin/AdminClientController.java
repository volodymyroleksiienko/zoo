package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.Client;
import com.charlie.zoo.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/clients")
@AllArgsConstructor
public class AdminClientController {
    private final ClientService clientService;

    @GetMapping
    public String getProducer(Model model){
        model.addAttribute("clients",clientService.findAll());
        return "admin/";
    }

    @PostMapping({"/add","/edit"})
    public String addProducer(Client client){
        clientService.save(client);
        return "redirect:/admin/clients";
    }

    @PostMapping("/delete")
    public String deleteProducer(int id){
        clientService.deleteByID(id);
        return "redirect:/admin/clients";
    }
}
