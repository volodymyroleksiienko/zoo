package com.charlie.zoo.controller.user;

import com.charlie.zoo.service.CookieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;

@Controller
@AllArgsConstructor
public class CartController {
    private final CookieService cookieService;

    @PostMapping("/addToCart")
    public void addToCart(@CookieValue(value = "id", defaultValue = "") String id,Model model, HttpServletResponse httpServletResponse,
                          Integer idOfPackageType, Integer count){
        cookieService.checkCookie(id,httpServletResponse,model);


    }
}
