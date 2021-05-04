package com.charlie.zoo.controller.admin;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/statistics")
@AllArgsConstructor
public class AdminStatisticsController {

    @GetMapping
    public String get(){
        return "admin/statistics";
    }

}
