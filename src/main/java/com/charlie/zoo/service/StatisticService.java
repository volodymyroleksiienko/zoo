package com.charlie.zoo.service;

import com.charlie.zoo.entity.dto.StatisticDto;

import java.text.ParseException;
import java.util.List;

public interface StatisticService {
    List<StatisticDto> getStatistic(String from, String to,Integer[] users) throws ParseException;
}
