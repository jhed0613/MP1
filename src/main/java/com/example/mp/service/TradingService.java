package com.example.mp.service;

import com.example.mp.dto.UserStockDto;

import java.util.List;

public interface TradingService {
    void buyStock(String username, String stockName, int quantity, String stockType);
    void sellStock(String username, String stockName, int quantity, String stockType);
    List<UserStockDto> getUserStockList(long userId);
}
