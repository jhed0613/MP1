package com.example.mp.repository;

import com.example.mp.entity.StockIndexEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockIndexRepository extends JpaRepository<StockIndexEntity, Integer> {
}
