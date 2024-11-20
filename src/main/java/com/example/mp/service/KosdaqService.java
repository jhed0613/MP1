package com.example.mp.service;

import com.example.mp.dto.KosdaqStockDto;
import com.example.mp.entity.KosdaqStockEntity;

import java.util.List;

public interface KosdaqService {
    List<KosdaqStockDto> getKosDaqStockList();
//    List<KosdaqStockDto> findByStockName(String stockName);
//    ResponseEntity<KosdaqStockDto> findByStockName(String stockName);

    void saveStocks(List<KosdaqStockDto> stockDtos);
    KosdaqStockDto EntityToDto(KosdaqStockEntity entity);
    KosdaqStockEntity dtoToEntity(KosdaqStockDto dto);
    void updateKosdaqData();
}
