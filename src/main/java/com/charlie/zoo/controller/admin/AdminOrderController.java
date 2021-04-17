package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.PackageType;
import com.charlie.zoo.entity.dto.PackageTypeDto;
import com.charlie.zoo.service.OrderDetailsService;
import com.charlie.zoo.service.OrderService;
import com.charlie.zoo.service.PackageTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @PostMapping("/orderReview")
    public String editOrder(OrderInfo orderInfo){
        orderService.update(orderInfo);
        return "redirect:/admin/orders/orderReview/"+orderInfo.getId();
    }

    @PostMapping("/addOrder")
    public String addOrder(OrderInfo orderInfo){
        orderService.save(orderInfo);
        return "redirect:/admin/orders";
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

    @PostMapping("/editDetail")
    public String getDetail(int id,int count,String currentUrl){
        OrderDetails details = orderDetailsService.findById(id);
        OrderInfo orderInfo = details.getOrderInfo();
        details.setCount(count);
        orderDetailsService.save(details);
        orderService.save(orderInfo);
        return "redirect:"+currentUrl;
    }

    @PostMapping("/addDetail")
    public String getDetail(String orderId,Integer count,Integer packId){
        UUID uuid = UUID.fromString(orderId);
        OrderDetails details = orderDetailsService.addProductToOrder(uuid,packId,count);
        OrderInfo orderInfo = details.getOrderInfo();
        orderService.save(orderInfo);
        return "redirect:/admin/orders/orderReview/"+orderId;
    }

    @ResponseBody
    @PostMapping("/findPackType")
    public List<PackageTypeDto> findPackType(String productName){
        return PackageTypeDto.convertToListDto(packageTypeService.findFirst10ByProductNameContaining(productName));
    }
}
