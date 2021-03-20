package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.Product;
import com.charlie.zoo.service.AnimalService;
import com.charlie.zoo.service.CategoryService;
import com.charlie.zoo.service.OrderService;
import com.charlie.zoo.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class MainController {
    private AnimalService animalService;
    private CategoryService categoryService;
    private OrderService orderService;
    private ProductService productService;

    @GetMapping("/")
    public String getIndex(@CookieValue(value = "id", defaultValue = "") String username, Model model,
                           HttpServletResponse httpServletResponse) {
        checkCookie(username,httpServletResponse,model);
        modelConfig(model);
        return "user/index";
    }

    @GetMapping("/shop")
    public String getShop(@CookieValue(value = "id", defaultValue = "") String username,Model model,
                          HttpServletResponse httpServletResponse,Integer[] category,Integer[] categoryItem,Integer[] producer,Double[] packSize){
        checkCookie(username,httpServletResponse,model);
        modelConfig(model);
        model.addAttribute("products",productService.getFilteredProduct(category,categoryItem,producer,packSize));
        return "user/shop";
    }

    @GetMapping("/singleProduct/{id}")
    public String getSingleProduct(@CookieValue(value = "id", defaultValue = "") String username,
                                   @PathVariable int id, Model model,HttpServletResponse httpServletResponse){
        modelConfig(model);
        checkCookie(username,httpServletResponse,model);
        Product product = productService.findById(id);
        model.addAttribute("product",product);
        return "user/product-details";
    }

    @GetMapping("/contact")
    public String getContact(@CookieValue(value = "id", defaultValue = "") String username,Model model,
                             HttpServletResponse httpServletResponse){
        checkCookie(username,httpServletResponse,model);
        modelConfig(model);
        return "user/contact";
    }

    @GetMapping("/cart")
    public String getCart(@CookieValue(value = "id", defaultValue = "") String username,Model model,
                          HttpServletResponse httpServletResponse){
        checkCookie(username,httpServletResponse,model);
        modelConfig(model);
        return "user/cart";
    }

    @GetMapping("/checkout")
    public String getCheckout(@CookieValue(value = "id", defaultValue = "") String username,Model model,
                              HttpServletResponse httpServletResponse){
        checkCookie(username,httpServletResponse,model);
        modelConfig(model);
        return "user/checkout";
    }

    private void modelConfig(Model model){
        model.addAttribute("animals",animalService.findAll());
        model.addAttribute("categories",categoryService.findAll());
    }

    public void checkCookie(String id, HttpServletResponse httpServletResponse, Model model){
        if(!id.isEmpty()) {
            OrderInfo orderInfo = orderService.findById(UUID.fromString(id));
            if(orderInfo!=null) {
                model.addAttribute("order", orderInfo);
                return;
            }
        }
        OrderInfo order = new OrderInfo();
        order.setId(UUID.randomUUID());
        order = orderService.save(order);
        httpServletResponse.addCookie(new Cookie("id", order.getId().toString()));
        model.addAttribute("order",order);
    }
}

