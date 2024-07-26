package com.example.mp.service;

import com.example.mp.dto.KorStockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StockService {
    List<KorStockDto> getKosPiStockList();
    List<KorStockDto> getKosDakStockList();

    List<KorStockDto> findByStockName(String stockName);
}
