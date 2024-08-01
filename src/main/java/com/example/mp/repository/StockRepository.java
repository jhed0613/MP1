package com.example.mp.repository;

import com.example.mp.entity.KospiStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface StockRepository extends JpaRepository<KospiStockEntity, String> {
    Optional<KospiStockEntity> findByStockName(String stockName);
}


