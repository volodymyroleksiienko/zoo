package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.HistoryDetails;
import com.charlie.zoo.entity.ProductHistory;
import com.charlie.zoo.service.HistoryDetailsService;
import com.charlie.zoo.service.ProductHistoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @PostMapping("/edit")
    public String editOrder(ProductHistory productHistory){
        productHistoryService.update(productHistory);
        return "redirect:/admin/history/historyReview/"+productHistory.getId();
    }

    @GetMapping("/add")
    public String addProducts(Model model){
        return "admin/singleHistory";
    }

    @PostMapping("/add")
    public String add(ProductHistory productHistory){
        productHistoryService.save(productHistory);
        return "redirect:/admin/history";
    }

    @GetMapping("/delete/{id}")
    public String deleteHistory(@PathVariable int id){
        productHistoryService.deleteByID(id);
        return "redirect:/admin/history";
    }

    @GetMapping("/deleteDetail")
    public String deleteDetail(int id){
        Integer historyId = historyDetailsService.findById(id).getProductHistory().getId();
        historyDetailsService.deleteByID(id);
        return "redirect:/admin/history/historyReview/"+historyId;
    }


    @PostMapping("/editDetail")
    public String editDetail(int historyId,HistoryDetails details){
        historyDetailsService.update(historyId,details);
        return "redirect:/admin/history/historyReview/"+historyId;
    }

    @PostMapping("/addDetail")
    public String getDetail(int historyId, HistoryDetails details){
        details.setProductHistory(productHistoryService.findById(historyId));
        historyDetailsService.save(details);
        return "redirect:/admin/history/historyReview/"+historyId;
    }
}
