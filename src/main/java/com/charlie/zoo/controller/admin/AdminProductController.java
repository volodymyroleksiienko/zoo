package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.service.ProducerService;
import com.charlie.zoo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
@AllArgsConstructor
public class AdminProductController {
    private ProductService productService;
    @GetMapping
    public String getProduct(Model model){
        model.addAttribute("products",productService.findAll());
        return "/admin/products";
    }

    @PostMapping({"/add","/edit"})
    public String addProduct(Product product){
        productService.save(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/delete")
    public String deleteProduct(int id){
        productService.deleteByID(id);
        return "redirect:/admin/products";
    }
}
