package com.example.mp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "kospi")
@Data
public class KospiStockEntity implements StockEntity{
    @Id
    private String stockName;
    private String no;
    private String price;
    private String diffAmount;
    private String dayRange;
    private String parValue;
    private String marketCap;
    private String numberOfListedShares;
    private String turnover;
    private String foreignOwnRate;
    private String per;
    private String roe;
//    private String discussionRoomUrl;
}
