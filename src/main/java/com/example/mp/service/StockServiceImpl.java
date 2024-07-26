package com.example.mp.service;

import com.example.mp.common.StockUtils;
import com.example.mp.dto.KorStockDto;
import com.example.mp.repository.KorStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    @Autowired
    private StockUtils stockUtils;

    @Autowired
    private KorStockRepository korStockRepository;

    @Override
    public List<KorStockDto> getKosPiStockList() {
        return stockUtils.getKorStockList(
                "https://finance.naver.com/sise/sise_market_sum.naver");
    }

    @Override
    public List<KorStockDto> getKosDakStockList() {
        return stockUtils.getKorStockList(
                "https://finance.naver.com/sise/sise_market_sum.naver?sosok=1");
    }

    @Override
    public List<KorStockDto> findByStockName(String stockName) {
        return korStockRepository.findByStockName(stockName);
    }
}
