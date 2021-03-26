package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.Animal;
import com.charlie.zoo.service.AnimalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/animal")
@AllArgsConstructor
public class AdminAnimalController {
    private AnimalService animalService;

    @GetMapping
    public String getAnimal(Model model){
        model.addAttribute("animals",animalService.findAll());
        return "admin/animal";
    }

    @PostMapping({"/add","/edit"})
    public String addAnimal(Animal animal, MultipartFile multipartFile){
        animalService.save(animal,multipartFile);
        return "redirect:/admin/animal";
    }

    @PostMapping("/delete")
    public String deleteAnimal(int id){
        animalService.deleteByID(id);
        return "redirect:/admin/animal";
    }
}
