package com.example.mp.repository;

import com.example.mp.entity.StockIndexHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockIndexHistoryRepository extends JpaRepository<StockIndexHistoryEntity, Integer> {
}
