package com.charlie.zoo.controller.admin;

import com.charlie.zoo.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.text.ParseException;

@Controller
@RequestMapping("/admin/statistics")
@AllArgsConstructor
public class AdminStatisticsController {
    private StatisticService statisticService;

    @GetMapping
    public String get(Model model,String from, String to) throws ParseException {
        if(from!=null && to!=null)
        model.addAttribute("statistic",statisticService.getStatistic(from, to));
        return "admin/statistics";
    }

}
