package com.example.mp.service.impl;

import com.example.mp.common.StockUtils;
import com.example.mp.dto.KosdaqStockDto;
import com.example.mp.entity.KosdaqStockEntity;
import com.example.mp.repository.KosdaqStockRepository;
import com.example.mp.service.KosdaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KosdaqServiceImpl implements KosdaqService {
    @Autowired
    private StockUtils stockUtils;
    @Autowired
    private KosdaqStockRepository kosdaqStockRepository;

    @Override
    public List<KosdaqStockDto> getKosDaqStockList() {
        return stockUtils.getKosdaqStockList(
                "https://finance.naver.com/sise/sise_market_sum.naver?sosok=1");
    }

//    @Override
//    public ResponseEntity<KosdaqStockDto> findByStockName(String stockName) {
//        return kosdaqStockRepository.findByStockName(stockName);
//    }

//    @Override
//    public List<KosdaqStockDto> findByStockName(String stockName) {
//        return kosdaqStockRepository.findByStockName(stockName);
//    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    // 정한 시간에 실행 , 사용하려면 메인 클래스에 @EnableScheduling 추가해야함.
    public void updateKosdaqData() {
        try {
            List<KosdaqStockDto> kosDaqStockList = getKosDaqStockList();
            List<KosdaqStockEntity> kosDaqEntities = kosDaqStockList.stream()
                    .map(this::dtoToEntity)
                    .collect(Collectors.toList());
            kosdaqStockRepository.saveAll(kosDaqEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveStocks(List<KosdaqStockDto> stockDtoList) {
        List<KosdaqStockEntity> entities = stockDtoList.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
        kosdaqStockRepository.saveAll(entities);
    }


    @Override
    public KosdaqStockEntity dtoToEntity(KosdaqStockDto dto) {
        KosdaqStockEntity entity = new KosdaqStockEntity();
        entity.setStockName(dto.getStockName());
        entity.setNo(dto.getNo());
        entity.setPrice(dto.getPrice());
        entity.setDiffAmount(dto.getDiffAmount());
        entity.setDayRange(dto.getDayRange());
        entity.setParValue(dto.getParValue());
        entity.setMarketCap(dto.getMarketCap());
        entity.setNumberOfListedShares(dto.getNumberOfListedShares());
        entity.setTurnover(dto.getTurnover());
//        entity.(dto.getDiscussionRoomUrl());
        entity.setForeignOwnRate(dto.getForeignOwnRate());
        entity.setPer(dto.getPer());
        entity.setRoe(dto.getRoe());
        return entity;
    }

    @Override
    public KosdaqStockDto EntityToDto(KosdaqStockEntity entity) {
        KosdaqStockDto dto = new KosdaqStockDto();
        dto.setStockName(entity.getStockName());
        dto.setNo(entity.getNo());
        dto.setPrice(entity.getPrice());
        dto.setDiffAmount(entity.getDiffAmount());
        dto.setDayRange(entity.getDayRange());
        dto.setParValue(entity.getParValue());
        dto.setMarketCap(entity.getMarketCap());
        dto.setNumberOfListedShares(entity.getNumberOfListedShares());
        dto.setTurnover(entity.getTurnover());
        dto.setForeignOwnRate(dto.getForeignOwnRate());
        dto.setPer(dto.getPer());
        dto.setRoe(dto.getRoe());
//        dto.setDiscussionRoomUrl(entity.getDiscussionRoomUrl());
        return dto;
    }
}
