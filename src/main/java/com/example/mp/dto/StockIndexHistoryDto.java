package com.example.mp.dto;

import com.example.mp.entity.StockIndexEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StockIndexHistoryDto {
    private int historyId;
    private int stockId;  // String 대신 int
    private LocalDateTime date;
    private double openVal;
    private double cloVal;
    private double highVal;
    private double lowVal;
    private double change;
    private LocalDateTime createTime;
}