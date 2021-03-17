package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.service.ProducerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/producer")
@AllArgsConstructor
public class AdminProducerController {
    private ProducerService producerService;
    @GetMapping
    public String getProducer(Model model){
        model.addAttribute("producers",producerService.findAll());
        return "admin/producer";
    }

    @PostMapping({"/add","/edit"})
    public String addProducer(Producer producer){
        producerService.save(producer);
        return "redirect:/admin/producer";
    }

    @PostMapping("/delete")
    public String deleteProducer(int id){
        producerService.deleteByID(id);
        return "redirect:/admin/producer";
    }
}
