package com.example.mp.service;

import com.example.mp.dto.KospiStockDto;
import com.example.mp.entity.KospiStockEntity;

import java.util.List;

public interface KospiService {
    List<KospiStockDto> getKosPiStockList();

    List<KospiStockDto> findByStockName(String stockName);

    void saveStocks(List<KospiStockDto> stockDtos);
    void updateKospiData();
    KospiStockDto entityToDto(KospiStockEntity entity);
    KospiStockEntity dtoToEntity(KospiStockDto dto);
}
