package com.example.mp.repository;

import com.example.mp.dto.KorStockDto;
import com.example.mp.entity.KorStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KorStockRepository extends JpaRepository<KorStockEntity, String> {
//    List<KorStockEntity> findByStockName(String stockName);
    List<KorStockDto> findByStockName(String stockName);
}
