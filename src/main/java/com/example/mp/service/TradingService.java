package com.example.mp.service;

public interface TradingService {
    void buyStock(String username, String stockName, int quantity, String stockType);
    void sellStock(String username, String stockName, int quantity, String stockType);
}
