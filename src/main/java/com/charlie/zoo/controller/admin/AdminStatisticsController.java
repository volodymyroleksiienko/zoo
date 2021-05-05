package com.charlie.zoo.controller.admin;

import com.charlie.zoo.entity.dto.StatisticDto;
import com.charlie.zoo.export.ExportStatistic;
import com.charlie.zoo.service.StatisticService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/admin/statistics")
@AllArgsConstructor
public class AdminStatisticsController {
    private final StatisticService statisticService;
    private final ExportStatistic exportStatistic;

    @GetMapping
    public String get(Model model, String from, String to) throws ParseException {
        if (from != null && to != null) {
            List<StatisticDto> list = statisticService.getStatistic(from, to);
            model.addAttribute("statistic", list);
            model.addAttribute("totalEarn", list.stream()
                    .flatMap(statisticDto -> Stream.of(statisticDto.getEarnMoney()))
                    .reduce(BigDecimal::add)
                    .orElse(new BigDecimal(0)));
            model.addAttribute("totalSpend", list.stream()
                    .flatMap(statisticDto -> Stream.of(statisticDto.getSpendMoney()))
                    .reduce(BigDecimal::add)
                    .orElse(new BigDecimal(0)));
            model.addAttribute("fromDate", from);
            model.addAttribute("toDate", to);
        }
        return "admin/statistics";
    }

    @GetMapping("/export")
    public ResponseEntity<Resource> export(String from, String to) throws ParseException, FileNotFoundException {

        String filePath = exportStatistic.generateReport(statisticService.getStatistic(from,to));
        File file = new File(filePath);
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
    }

}
