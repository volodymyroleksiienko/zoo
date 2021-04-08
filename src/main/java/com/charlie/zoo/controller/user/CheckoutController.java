package com.charlie.zoo.controller.user;

import com.charlie.zoo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@Controller
@AllArgsConstructor
@RequestMapping("/checkout")
public class CheckoutController {
    private final AnimalService animalService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CookieService cookieService;
    private final OrderService orderService;
    private final LiqPayDataService liqPayDataService;

    @GetMapping
    public String getCheckout(@CookieValue(value = "id", defaultValue = "") String username, Model model,
                              HttpServletResponse httpServletResponse){
        username = cookieService.checkCookie(username,httpServletResponse,model);
        //@todo
        String data = liqPayDataService.generateData("0.01", UUID.randomUUID().toString());
        model.addAttribute("paymentData",data);
        model.addAttribute("paymentSignature",liqPayDataService.generateSignature(data));
        modelConfig(model,username);
        return "user/checkout";
    }

    @PostMapping
    public String post(String data, String signature) {
        Map<String,String> map = liqPayDataService.decodeData(data,signature);
        orderService.checkOrder(map);
        return "user/successful-payment";
    }

    @PostMapping("/successful")
    public String successful(Model model){
        System.out.println("Successfull request");
        return "user/successful-payment";
    }

    @GetMapping("/successful")
    public String get(Model model){
        System.out.println("Successfull request");
        return "user/successful-payment";
    }

    private void modelConfig(Model model,String username){
        model.addAttribute("animals",animalService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("orderInfo",orderService.findById(UUID.fromString(username)));
    }
}
