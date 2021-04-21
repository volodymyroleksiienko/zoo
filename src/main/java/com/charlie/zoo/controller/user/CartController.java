package com.charlie.zoo.controller.user;

import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.dto.OrderInfoDto;
import com.charlie.zoo.service.CookieService;
import com.charlie.zoo.service.OrderDetailsService;
import com.charlie.zoo.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
@AllArgsConstructor
public class CartController {
    private final CookieService cookieService;
    private final OrderService orderService;
    private final OrderDetailsService orderDetailsService;

    @PostMapping("/addToCart")
    public void addToCart(@CookieValue(value = "id", defaultValue = "") String id,Model model, HttpServletResponse httpServletResponse,
                          Integer idOfPackageType, Integer count){
        cookieService.checkCookie(id,httpServletResponse,model);
        orderDetailsService.addProductToOrder(UUID.fromString(id),idOfPackageType,count);
    }

    @PostMapping("/changeCount")
    public void changeCount(@CookieValue(value = "id", defaultValue = "") String id,Model model, HttpServletResponse httpServletResponse,
                          Integer idOfPackageType, Integer count){
        cookieService.checkCookie(id,httpServletResponse,model);
        orderDetailsService.changeCount(idOfPackageType,count);
    }

    @GetMapping("/addToCart/{idOfPackageType}")
    public String addToCartOne(@CookieValue(value = "id", defaultValue = "") String id, Model model, HttpServletResponse httpServletResponse,
                             @PathVariable int idOfPackageType){
        id = cookieService.checkCookie(id,httpServletResponse,model);
        OrderDetails details;
        if (!id.isEmpty()) {
            details = orderDetailsService.addProductToOrder(UUID.fromString(id), idOfPackageType, 1);
        }else {
            details = orderDetailsService.addProductToOrder(null, idOfPackageType, 1);
        }
        cookieService.updateCookie(details.getOrderInfo().getId().toString(),httpServletResponse,model);
        return "redirect:/cart";
    }

    @ResponseBody
    @PostMapping("/addToCart/{idOfPackageType}")
    public OrderInfoDto addToCartRest(@CookieValue(value = "id", defaultValue = "") String id, Model model, HttpServletResponse httpServletResponse,
                                     @PathVariable int idOfPackageType){
        id = cookieService.checkCookie(id,httpServletResponse,model);
        OrderDetails details;
        if (!id.isEmpty()) {
            details = orderDetailsService.addProductToOrder(UUID.fromString(id), idOfPackageType, 1);
        }else {
            details = orderDetailsService.addProductToOrder(null, idOfPackageType, 1);
        }
        cookieService.updateCookie(details.getOrderInfo().getId().toString(),httpServletResponse,model);
        return OrderInfoDto.convertToDto(details.getOrderInfo());
    }

    @ResponseBody
    @PostMapping("/removeFromCart")
    public OrderInfoDto removeFromCartRest(Integer id, String uuid){
        OrderInfo orderInfo = orderDetailsService.delete(id,uuid);
        return OrderInfoDto.convertToDto(orderInfo);
    }


    @GetMapping("/removeFromCart")
    public String removeFromCart(Integer id, String uuid){
        orderDetailsService.delete(id,uuid);
        return "redirect:/cart";
    }
}
