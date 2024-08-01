package com.example.mp.dto;

import java.time.LocalDate;

public class UserStockDto {
    private Long id;
    private Long userId;
    private String stockName; // KospiStockEntity와 연동
    private String quantity;
    private String averagePrice;
    private LocalDate purchaseDate;
}
