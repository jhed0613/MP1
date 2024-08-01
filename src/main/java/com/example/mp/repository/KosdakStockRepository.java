package com.example.mp.repository;

import com.example.mp.dto.KosdakStockDto;
import com.example.mp.entity.KosdakStockEntity;
import com.example.mp.entity.KospiStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KosdakStockRepository extends JpaRepository<KosdakStockEntity, String> {
//    List<KosdakStockDto> findByStockName(String stockName);
    List<KospiStockEntity> findByStockName(String stockName);
}
