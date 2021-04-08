package com.charlie.zoo.controller.admin;

import com.charlie.zoo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

//    @PostMapping("/modalUpdate")
//    public S
}
