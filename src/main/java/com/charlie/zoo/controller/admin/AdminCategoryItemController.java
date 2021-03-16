package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.CategoryItem;
import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.service.AnimalService;
import com.charlie.zoo.service.CategoryItemService;
import com.charlie.zoo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/categoryItem")
@AllArgsConstructor
public class AdminCategoryItemController {
    private final CategoryService categoryService;
    private final AnimalService animalService;
    private CategoryItemService categoryItemService;

    @GetMapping("/{animalId}/{itemId}")
    public String getCategoryItem(@PathVariable int animalId,@PathVariable int itemId, Model model){
        model.addAttribute("activeTabId",animalId);
        model.addAttribute("animals",animalService.findAll());
        model.addAttribute("categoryItems",categoryItemService.findAll());
        return "/admin/categoryItem";
    }

    @PostMapping({"/add","/edit"})
    public String addCategoryItem(CategoryItem categoryItem){
        categoryItemService.save(categoryItem);
        return "redirect:/admin/categoryItem";
    }

    @PostMapping("/delete")
    public String deleteCategoryItem(int id){
        categoryItemService.deleteByID(id);
        return "redirect:/admin/categoryItem";
    }
}
