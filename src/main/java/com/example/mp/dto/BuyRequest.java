package com.example.mp.dto;

public class BuyRequest {
    private String username;
    private String stockName;
    private int quantity;
    private boolean isKospi;

    public BuyRequest() {}

    public String getUsername() {
        return username;
    }
    public boolean isKospi() { return isKospi; }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
