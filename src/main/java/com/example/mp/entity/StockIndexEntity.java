package com.example.mp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "StockIndex")
@Data
public class StockIndexEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="stock_id")
    private int stockId;

    @Column(name="index_name", nullable = false)
    private String indexName;

    @Column(name="current_value")
    private double curVal;

    @Column(name="created_at")
    private LocalDateTime createTime = LocalDateTime.now();
}
