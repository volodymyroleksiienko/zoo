package com.charlie.zoo.entity.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class StatisticDto {
    private String nameOfProduct;
    private String packageType;
    private BigDecimal spendMoney;
    private BigDecimal earnMoney;
    private BigDecimal totalMoney;
    private int getCount;
    private int sellCount;
    private int beforeCount;
    private int totalCount;

}
