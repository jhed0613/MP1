package com.example.mp.service.impl;

import com.example.mp.common.StockUtils;
import com.example.mp.dto.KospiStockDto;
import com.example.mp.entity.KospiStockEntity;
import com.example.mp.repository.KospiStockRepository;
import com.example.mp.service.KospiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class KospiServiceImpl implements KospiService {

    @Autowired
    private StockUtils stockUtils;

    @Autowired
    private KospiStockRepository kospiStockRepository;

    @Override
    public List<KospiStockDto> getKosPiStockList() {
        return stockUtils.getKospiStockList(
                "https://finance.naver.com/sise/sise_market_sum.naver");
    }

    @Override
    public List<KospiStockDto> findByStockName(String stockName) {
        List<KospiStockEntity> entities = kospiStockRepository.findByStockName(stockName);
        return entities.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }
//    @Override
//    public List<KospiStockDto> findByStockName(String stockName) {
//        return kospiStockRepository.findByStockName(stockName);
//    }

    // SRP(단일 책임 원칙)을 유지하고, 컨트롤러는 단순히 요청을 처리하고 응답을 반환하는 역할만 하게 함.
    @Override
    public void saveStocks(List<KospiStockDto> stockDtoList) {
        List<KospiStockEntity> entities = stockDtoList.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
        kospiStockRepository.saveAll(entities);
    }
    @Scheduled(fixedRate = 60000) // 1분마다 실행
    public void updateKospiData() {
        try {
            List<KospiStockDto> kosPiStockList = getKosPiStockList();
            List<KospiStockEntity> kosPiEntities = kosPiStockList.stream()
                    .map(this::dtoToEntity)
                    .collect(Collectors.toList());
            kospiStockRepository.saveAll(kosPiEntities);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 클라이언트로 받은 데이터를 DTO로 변환 후 서비스 계층으로 이동 -> 서비스 계층에서 Entity 로 변환 후 데이터베이스 저장
    // DTO는 외부와의 데이터 전송에 적합한 구조를 가지고 Entity는 데이터베이스와의 상호작용에 적합한 구조를 가짐
    // DTO 에 보안상의 이유로 민감한 정보를 포함시키지 않음으로써 보안을 향상시킬 수 있음.
    @Override
    public KospiStockEntity dtoToEntity(KospiStockDto dto) {
        KospiStockEntity entity = new KospiStockEntity();
        entity.setStockName(dto.getStockName());
        entity.setNo(dto.getNo());
        entity.setPrice(dto.getPrice());
        entity.setDiffAmount(dto.getDiffAmount());
        entity.setDayRange(dto.getDayRange());
        entity.setParValue(dto.getParValue());
        entity.setMarketCap(dto.getMarketCap());
        entity.setNumberOfListedShares(dto.getNumberOfListedShares());
        entity.setTurnover(dto.getTurnover());
        entity.setForeignOwnRate(dto.getForeignOwnRate());
        entity.setPer(dto.getPer());
        entity.setRoe(dto.getRoe());
//        entity.setDiscussionRoomUrl(dto.getDiscussionRoomUrl());
        return entity;
    }

    @Override
    public KospiStockDto entityToDto(KospiStockEntity entity) {
        KospiStockDto dto = new KospiStockDto();
        dto.setStockName(entity.getStockName());
        dto.setNo(entity.getNo());
        dto.setPrice(entity.getPrice());
        dto.setDiffAmount(entity.getDiffAmount());
        dto.setDayRange(entity.getDayRange());
        dto.setParValue(entity.getParValue());
        dto.setMarketCap(entity.getMarketCap());
        dto.setNumberOfListedShares(entity.getNumberOfListedShares());
        dto.setTurnover(entity.getTurnover());
        dto.setForeignOwnRate(entity.getForeignOwnRate());
        dto.setPer(entity.getPer());
        dto.setRoe(entity.getRoe());
//        dto.setDiscussionRoomUrl(entity.getDiscussionRoomUrl());
        return dto;
    }
}
