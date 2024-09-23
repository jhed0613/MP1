package com.example.mp.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserStockDto {
    private Long id;
    private Long userId;
    private String stockName;
    private String quantity;
    private String averagePrice;
    private String stockType;
    private double totalPrice;
    private LocalDate purchaseDate;
    private double TotalCurrentValue;
    private double profit;
}
