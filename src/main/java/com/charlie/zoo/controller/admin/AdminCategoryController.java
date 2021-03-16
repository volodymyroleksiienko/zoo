package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.Category;
import com.charlie.zoo.service.AnimalService;
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
    private final CategoryService categoryService;
    private final AnimalService animalService;
    @GetMapping
    public String getCategory(Model model,Integer id){
        model.addAttribute("animals",animalService.findAll());
        if(id==null){
            model.addAttribute("activeTabId",1);
            model.addAttribute("categories",categoryService.findAll());
        }else {
            model.addAttribute("activeTabId",id);
            model.addAttribute("categories",categoryService.findByAnimalId(id));
        }
        return "/admin/categories";
    }

    @PostMapping({"/add","/edit"})
    public String addAnimal(Category category){
        categoryService.save(category);
        return "redirect:/admin/category?id="+category.getAnimal().getId();
    }

    @PostMapping("/delete")
    public String deleteAnimal(int id){
        int animalId = categoryService.findById(id).getAnimal().getId();
        categoryService.deleteByID(id);
        return "redirect:/admin/category?id="+animalId;
    }
}
