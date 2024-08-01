package com.example.mp.service;

import com.example.mp.dto.KosdakStockDto;
import com.example.mp.dto.KospiStockDto;
import com.example.mp.entity.KosdakStockEntity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface KosdakService {
    List<KosdakStockDto> getKosDakStockList();
//    List<KosdakStockDto> findByStockName(String stockName);
//    ResponseEntity<KosdakStockDto> findByStockName(String stockName);

    void saveStocks(List<KosdakStockDto> stockDtos);
    KosdakStockDto EntityToDto(KosdakStockEntity entity);
    KosdakStockEntity dtoToEntity(KosdakStockDto dto);
    void updateKosdakData();
}
