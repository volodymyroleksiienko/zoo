package com.charlie.zoo.controller;

import com.charlie.zoo.entity.Animal;
import com.charlie.zoo.entity.Category;
import com.charlie.zoo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/category")
@AllArgsConstructor
public class AdminCategoryController {
    private CategoryService categoryService;
    @GetMapping
    public String getCategory(Model model){
        model.addAttribute("categories",categoryService.findAll());
        return "/admin/categories";
    }

    @PostMapping({"/add","/edit"})
    public String addAnimal(Category animal){
        categoryService.save(animal);
        return "redirect:/admin/animal";
    }

    @PostMapping("/delete")
    public String deleteAnimal(int id){
        categoryService.deleteByID(id);
        return "redirect:/admin/animal";
    }
}
