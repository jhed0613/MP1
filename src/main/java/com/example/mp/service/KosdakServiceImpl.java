package com.example.mp.service;

import com.example.mp.common.StockUtils;
import com.example.mp.dto.KosdakStockDto;
import com.example.mp.dto.KospiStockDto;
import com.example.mp.entity.KosdakStockEntity;
import com.example.mp.entity.KospiStockEntity;
import com.example.mp.repository.KosdakStockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KosdakServiceImpl implements KosdakService{
    @Autowired
    private StockUtils stockUtils;
    @Autowired
    private KosdakStockRepository kosdakStockRepository;

    @Override
    public List<KosdakStockDto> getKosDakStockList() {
        return stockUtils.getKosdakStockList(
                "https://finance.naver.com/sise/sise_market_sum.naver?sosok=1");
    }

//    @Override
//    public ResponseEntity<KosdakStockDto> findByStockName(String stockName) {
//        return kosdakStockRepository.findByStockName(stockName);
//    }

//    @Override
//    public List<KosdakStockDto> findByStockName(String stockName) {
//        return kosdakStockRepository.findByStockName(stockName);
//    }

    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateKosdakData() {
        try {
            List<KosdakStockDto> kosDakStockList = getKosDakStockList();
            List<KosdakStockEntity> kosDakEntities = kosDakStockList.stream()
                    .map(this::dtoToEntity)
                    .collect(Collectors.toList());
            kosdakStockRepository.saveAll(kosDakEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    @Scheduled(cron = "0 0 0 * * *") // 정한 시간에 실행 , 사용하려면 메인 클래스에 @EnableScheduling 추가해야함.
//    public void updateKosdakStockData() {
//        List<KosdakStockDto> kosDakStockList = this.getKosDakStockList();
//        List<KosdakStockEntity> kosDakEntities = kosDakStockList.stream()
//                .map(this::dtoToEntity)
//                .collect(Collectors.toList());
//        kosdakStockRepository.saveAll(kosDakEntities);
//    }

    @Override
    public void saveStocks(List<KosdakStockDto> stockDtoList) {
        List<KosdakStockEntity> entities = stockDtoList.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
        kosdakStockRepository.saveAll(entities);
    }


    @Override
    public KosdakStockEntity dtoToEntity(KosdakStockDto dto) {
        KosdakStockEntity entity = new KosdakStockEntity();
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
    public KosdakStockDto EntityToDto(KosdakStockEntity entity) {
        KosdakStockDto dto = new KosdakStockDto();
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
