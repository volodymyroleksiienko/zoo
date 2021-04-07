package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.Product;
import com.charlie.zoo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class MainController {
    private final AnimalService animalService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CookieService cookieService;
    private final OrderService orderService;
    private final LiqPayDataService liqPayDataService;

    @GetMapping("/")
    public String getIndex(@CookieValue(value = "id", defaultValue = "") String username, Model model,
                           HttpServletResponse httpServletResponse) {
        cookieService.checkCookie(username,httpServletResponse,model);
        modelConfig(model,username);
        return "user/index";
    }

    @PostMapping
    public String post(String data, String signature){
        System.out.println(data);
        System.out.println(liqPayDataService.decodeData(data));
        return "redirect:/";
    }


    @GetMapping("/singleProduct/{id}")
    public String getSingleProduct(@CookieValue(value = "id", defaultValue = "") String username,
                                   @PathVariable int id, Model model,HttpServletResponse httpServletResponse){
        modelConfig(model,username);
        cookieService.checkCookie(username,httpServletResponse,model);
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "user/product-details";
    }

    @GetMapping("/contact")
    public String getContact(@CookieValue(value = "id", defaultValue = "") String username,Model model,
                             HttpServletResponse httpServletResponse){
        cookieService.checkCookie(username,httpServletResponse,model);
        modelConfig(model,username);
        return "user/contact";
    }

    @GetMapping("/cart")
    public String getCart(@CookieValue(value = "id", defaultValue = "") String username,Model model,
                          HttpServletResponse httpServletResponse){
        cookieService.checkCookie(username,httpServletResponse,model);
        model.addAttribute("orderInfo",orderService.findById(UUID.fromString(username)));
        modelConfig(model,username);
        return "user/cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(@CookieValue(value = "id", defaultValue = "") String username,Model model,
                              HttpServletResponse httpServletResponse){
        cookieService.checkCookie(username,httpServletResponse,model);
        String data = liqPayDataService.generateData("2.5",UUID.randomUUID().toString());
        model.addAttribute("paymentData",data);
        model.addAttribute("paymentSignature",liqPayDataService.generateSignature(data));
        modelConfig(model,username);
        return "user/checkout";
    }

    private void modelConfig(Model model,String username){
        model.addAttribute("animals",animalService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("orderInfo",orderService.findById(UUID.fromString(username)));
    }

}

