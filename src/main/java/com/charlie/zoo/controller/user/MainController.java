package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.Animal;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.service.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class MainController {
    private final AnimalService animalService;
    private final CategoryService categoryService;
    private final ProductService productService;
    private final CookieService cookieService;
    private final OrderService orderService;
    private final BannerService bannerService;
    private final LiqPayDataService liqPayDataService;

    @GetMapping("/")
    public String getIndex(@CookieValue(value = "id", defaultValue = "") String username, Model model,
                           HttpServletResponse httpServletResponse) {
        username = cookieService.checkCookie(username,httpServletResponse,model);
        modelConfig(model,username);
        model.addAttribute("ban1",bannerService.findById(1));
        model.addAttribute("ban2",bannerService.findById(2));
        model.addAttribute("ban3",bannerService.findById(3));
        model.addAttribute("ban4",bannerService.findById(4));
        List<Animal> animals = animalService.findAll();
        model.addAttribute("animals", animals);
        return "user/index";
    }



    @GetMapping("/singleProduct/{id}")
    public String getSingleProduct(@CookieValue(value = "id", defaultValue = "") String username,
                                   @PathVariable int id, Model model,HttpServletResponse httpServletResponse){
        modelConfig(model,username);
        username = cookieService.checkCookie(username,httpServletResponse,model);
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "user/product-details";
    }

    @GetMapping("/contact")
    public String getContact(@CookieValue(value = "id", defaultValue = "") String username,Model model,
                             HttpServletResponse httpServletResponse){
        username = cookieService.checkCookie(username,httpServletResponse,model);
        modelConfig(model,username);
        return "user/contact";
    }

    @GetMapping("/about")
    public String getAbout(@CookieValue(value = "id", defaultValue = "") String username,Model model,
                             HttpServletResponse httpServletResponse){
        username = cookieService.checkCookie(username,httpServletResponse,model);
        modelConfig(model,username);
        return "user/about";
    }

    @GetMapping("/cart")
    public String getCart(@CookieValue(value = "id", defaultValue = "") String username,Model model,
                          HttpServletResponse httpServletResponse){
        username = cookieService.checkCookie(username,httpServletResponse,model);
        model.addAttribute("orderInfo",orderService.findById(UUID.fromString(username)));
        modelConfig(model,username);
        return "user/cart";
    }


    private void modelConfig(Model model,String username){
        model.addAttribute("animals",animalService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        model.addAttribute("orderInfo",orderService.findById(UUID.fromString(username)));
    }

}

