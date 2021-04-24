package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.HistoryDetails;
import com.charlie.zoo.entity.OrderDetails;
import com.charlie.zoo.entity.OrderInfo;
import com.charlie.zoo.entity.ProductHistory;
import com.charlie.zoo.entity.dto.PackageTypeDto;
import com.charlie.zoo.service.HistoryDetailsService;
import com.charlie.zoo.service.ProductHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/history")
@AllArgsConstructor
public class AdminHistoryController {
    private final ProductHistoryService productHistoryService;
    private final HistoryDetailsService historyDetailsService;


    @GetMapping
    public String get(Model model){
        model.addAttribute("history",productHistoryService.findAll());
        return "admin/history";
    }


    @GetMapping("/historyReview/{historyId}")
    public String editProducts(@PathVariable int historyId, Model model){
        model.addAttribute("history", productHistoryService.findById(historyId));
        return "admin/singleHistory";
    }

//    @PostMapping("/orderReview")
//    public String editOrder(OrderInfo orderInfo){
//        orderService.update(orderInfo);
//        return "redirect:/admin/orders/orderReview/"+orderInfo.getId();
//    }

    @PostMapping("/add")
    public String add(ProductHistory productHistory){
        productHistoryService.save(productHistory);
        return "redirect:/admin/history";
    }

    @PostMapping("/delete")
    public String findPackType(int id,String currentUrl){
        HistoryDetails details = historyDetailsService.findById(id);
        historyDetailsService.deleteByID(id);
        return "redirect:"+currentUrl;
    }

//    @PostMapping("/editDetail")
//    public String getDetail(int id,int count,String currentUrl){
//        OrderDetails details = historyDetailsService.findById(id);
//        OrderInfo orderInfo = details.getOrderInfo();
//        details.setCount(count);
//        orderDetailsService.save(details);
//        orderService.save(orderInfo);
//        return "redirect:"+currentUrl;
//    }

//    @PostMapping("/addDetail")
//    public String getDetail(String orderId,Integer count,Integer packId){
//        UUID uuid = UUID.fromString(orderId);
//        OrderDetails details = orderDetailsService.addProductToOrder(uuid,packId,count);
//        OrderInfo orderInfo = details.getOrderInfo();
//        orderService.save(orderInfo);
//        return "redirect:/admin/orders/orderReview/"+orderId;
//    }
}
