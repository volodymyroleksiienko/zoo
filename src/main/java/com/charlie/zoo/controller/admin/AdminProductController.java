package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.Category;
import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.entity.Producer;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.entity.dto.PackageTypeDto;
import com.charlie.zoo.service.CategoryItemService;
import com.charlie.zoo.service.CategoryService;
import com.charlie.zoo.service.ProducerService;
import com.charlie.zoo.service.ProductService;
import lombok.AllArgsConstructor;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

    @PostMapping(value = "/add",consumes = {"application/x-www-form-urlencoded"})
    public String addProduct( String packageTypes){


        JSONArray jsonArray = new JSONArray();
        JSONObject jsonObject = new JSONObject();
        System.out.println(packageTypes);
//        productService.save(product,multipartFile);
        return "redirect:/admin/products";
    }

    @GetMapping("/add")
    public String addProducts(Model model){
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("items",categoryItemService.findAll());
        model.addAttribute("producers",producerService.findAll());
        model.addAttribute("products",productService.findAll());
        return "admin/addProduct";
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
