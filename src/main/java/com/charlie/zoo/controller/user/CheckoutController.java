package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.Servlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
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
        double price = orderService.getSummaryPrice(orderService.findById(UUID.fromString(username)));

        System.out.println(orderService.findById(UUID.fromString(username)));
        System.out.println(String.format("%.2f",price).replace(",","."));
        String data = liqPayDataService.generateData(String.format("%.2f",price).replace(",","."), username);
        model.addAttribute("paymentData",data);
        model.addAttribute("paymentSignature",liqPayDataService.generateSignature(data));
        modelConfig(model,username);
        return "user/checkout";
    }

    @PostMapping("/orderSubmit")
    public void order(OrderInfo orderInfo,String nameOfClient,String phoneNumber, HttpServletRequest request, HttpServletResponse response, Model model){
        orderService.submitOrder(orderInfo,nameOfClient,phoneNumber);
        Cookie cookie = new Cookie("id", "");
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    @PostMapping
    public String post(String data, String signature) {
        Map<String,String> map = liqPayDataService.decodeData(data,signature);
        orderService.checkOrder(map);
        return "user/successful-payment";
    }

    @PostMapping("/successful")
    public String successful(Model model){
        System.out.println("Successful request");
        return "user/successful-payment";
    }

    @GetMapping("/successful")
    public String get(Model model){
        return "user/successful-payment";
    }

    private void modelConfig(Model model,String username){
        model.addAttribute("animals",animalService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("orderInfo",orderService.findById(UUID.fromString(username)));
    }
}
