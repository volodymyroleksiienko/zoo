package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.service.CategoryItemService;
import com.charlie.zoo.service.CategoryService;
import com.charlie.zoo.service.ProducerService;
import com.charlie.zoo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/admin/products")
@AllArgsConstructor
public class AdminProductController {
    private final ProductService productService;
    private final ProducerService producerService;
    private final CategoryService categoryService;
    private final CategoryItemService categoryItemService;

    @GetMapping
    public String getProduct(Model model){
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("items",categoryItemService.findAll());
        model.addAttribute("producers",producerService.findAll());
        model.addAttribute("products",productService.findAll());
        return "admin/products";
    }

    @PostMapping("/add")
    public String addProduct(Product product,@RequestParam  MultipartFile multipartFile){
        productService.save(product,multipartFile);
        return "redirect:/admin/products";
    }

    @PostMapping("/edit")
    public String editProduct(Product product,@RequestParam MultipartFile multipartFile){
        productService.update(product,multipartFile);
        return "redirect:/admin/products";
    }

    @PostMapping("/delete")
    public String deleteProduct(int id){
        productService.deleteByID(id);
        return "redirect:/admin/products";
    }
}
