package com.example.mp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "StockIndexHistory")
@Data
public class StockIndexHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int historyId;

    @Column(name = "stock_id", insertable = false, updatable = false)
    private String stockId;

    @Column(name="date")
    private LocalDateTime date;

    @Column(name="open_value")
    private double openVal;

    @Column(name="close_value")
    private double cloVal;

    @Column(name="high_value")
    private double highVal;

    @Column(name="low_value")
    private double lowVal;

    @Column(name = "`change`")
    private double change;

    @Column(name="created_at")
    private LocalDateTime createTime = LocalDateTime.now();
//
//    @ManyToOne
//    @JoinColumn(name = "stock_id", referencedColumnName = "stock_id")
//    private StockIndexEntity stockIndex;
}
