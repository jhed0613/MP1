package com.example.mp.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class StockIndexDto {
    private int stockId;
    private String indexName;
    private double curVal;
    private LocalDateTime createTime;
}
