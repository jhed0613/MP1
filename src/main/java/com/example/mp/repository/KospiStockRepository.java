package com.example.mp.repository;

import com.example.mp.dto.KospiStockDto;
import com.example.mp.entity.KospiStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KospiStockRepository extends JpaRepository<KospiStockEntity, String> {
//    List<KorStockEntity> findByStockName(String stockName);
    List<KospiStockEntity> findByStockName(String stockName);

}
