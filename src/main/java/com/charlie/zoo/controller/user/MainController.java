package com.charlie.zoo.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public String getIndex(){
        return "user/index";
    }

    @GetMapping("/shop")
    public String getShop(){
        return "user/shop";
    }

    @GetMapping("/contact")
    public String getContact(){
        return "user/contact";
    }

    @GetMapping("/cart")
    public String getCart(){
        return "user/cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(){
        return "user/checkout";
    }
}

