package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.service.OrderDetailsService;
import com.charlie.zoo.service.OrderService;
import com.charlie.zoo.service.PackageTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin/orders")
@AllArgsConstructor
public class AdminOrderController {
    private final OrderService orderService;
    private final OrderDetailsService orderDetailsService;
    private final PackageTypeService packageTypeService;

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
        return "admin/singleOrder";
    }

    @PostMapping("/deleteDetail")
    public String findPackType(int id,String currentUrl){
        OrderDetails details = orderDetailsService.findById(id);
        OrderInfo orderInfo = details.getOrderInfo();
        orderInfo.getOrderDetails().remove(details);
        orderDetailsService.deleteByID(id);
        orderService.save(orderInfo);
        return "redirect:"+currentUrl;
    }
    @PostMapping("/deleteDetail")
    public String findPackType(int id,String currentUrl){
        OrderDetails details = orderDetailsService.findById(id);
        OrderInfo orderInfo = details.getOrderInfo();
        orderInfo.getOrderDetails().remove(details);
        orderDetailsService.deleteByID(id);
        orderService.save(orderInfo);
        return "redirect:"+currentUrl;
    }

    @ResponseBody
    @GetMapping("/findPackType")
    public String findPackType(String productName){
        return packageTypeService.findFirst2ByProductNameContaining(productName).toString();
    }
}
