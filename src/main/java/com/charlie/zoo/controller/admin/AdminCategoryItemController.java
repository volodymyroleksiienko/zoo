package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.CategoryItem;
import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.service.CategoryItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/categoryItem")
@AllArgsConstructor
public class AdminCategoryItemController {
    private CategoryItemService categoryItemService;

    @GetMapping
    public String getCategoryItem(Model model){
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
