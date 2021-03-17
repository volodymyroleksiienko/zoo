package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.CategoryItem;
import com.charlie.zoo.service.AnimalService;
import com.charlie.zoo.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class MainController {
    private AnimalService animalService;
    private CategoryService categoryService;

    @GetMapping("/")
    public String getIndex(Model model) {
        modelConfig(model);
        return "user/index";
    }

    @GetMapping("/shop")
    public String getShop(Model model){
        modelConfig(model);
        return "user/shop";
    }

    @GetMapping("/contact")
    public String getContact(Model model){
        modelConfig(model);
        return "user/contact";
    }

    @GetMapping("/cart")
    public String getCart(Model model){
        modelConfig(model);
        return "user/cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(Model model){
        modelConfig(model);
        return "user/checkout";
    }

    private void modelConfig(Model model){
        model.addAttribute("animals",animalService.findAll());
        model.addAttribute("categories",categoryService.findAll());
    }
}

