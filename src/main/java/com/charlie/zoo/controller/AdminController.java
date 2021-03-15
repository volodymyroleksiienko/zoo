package com.charlie.zoo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("/products")
    public String getProducts(){
        return "animal";
    }

    @GetMapping("/categories")
    public String getCategories(){
        return "admin/categories";
    }
}
