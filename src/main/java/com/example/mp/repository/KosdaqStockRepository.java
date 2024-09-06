package com.example.mp.repository;

import com.example.mp.entity.KosdaqStockEntity;
import com.example.mp.entity.KospiStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KosdaqStockRepository extends JpaRepository<KosdaqStockEntity, String> {
//    List<KosdakStockDto> findByStockName(String stockName);
    List<KosdaqStockEntity> findByStockName(String stockName);
}
