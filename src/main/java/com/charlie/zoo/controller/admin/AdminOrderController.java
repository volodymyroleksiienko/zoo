package com.charlie.zoo.controller.admin;

import com.charlie.zoo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Controller
@RequestMapping("/admin/orders")
@AllArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;

    @GetMapping
    public String get(Model model){
        model.addAttribute("orders",orderService.findAll());
        return "admin/orders";
    }

    @PostMapping("/modalUpdate")
    public String modalUpdate(String id,String statusOfPayment,String statusOfOrder){
        orderService.checkStatus(id,statusOfPayment,statusOfOrder);
        return "redirect:/admin/orders";
    }

    @GetMapping("/orderReview/{orderId}")
    public String editProducts(@PathVariable UUID orderId, Model model){
        model.addAttribute("order", orderService.findById(orderId));
//        model.addAttribute("productMainImg",imageService.findMainByProductId(productId));
//        model.addAttribute("categories",categoryService.findAll());
//        model.addAttribute("items",categoryItemService.findAll());
//        model.addAttribute("producers",producerService.findAll());
//        model.addAttribute("products",productService.findAll());
        return "admin/singleOrder";
    }
}
