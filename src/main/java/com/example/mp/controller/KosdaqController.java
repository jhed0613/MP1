package com.example.mp.controller;


import com.example.mp.dto.KosdaqStockDto;
import com.example.mp.entity.KosdaqStockEntity;
import com.example.mp.repository.KosdaqStockRepository;
import com.example.mp.service.KosdaqService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class KosdaqController {
    @Autowired
    private KosdaqService kosdaqService;
    @Autowired
    private KosdaqStockRepository kosdaqStockRepository;

    @GetMapping("/kosdaq")
    public List<KosdaqStockDto> getKosDaqStockList(HttpServletRequest request) {
        return kosdaqService.getKosDaqStockList();
    }
//    @GetMapping("/kosdaq/list")
//    public String listKosdaqStocks(Model model) {
//        List<KosdaqStockEntity> stocks = kosdaqStockRepository.findAll();
//        if (stocks.isEmpty()) {
//            // 데이터가 없으면 크롤링하여 데이터베이스에 저장
//            kosdaqService.updateKosdaqData(); // 크롤링 및 저장 메서드 호출
//            stocks = kosdaqStockRepository.findAll(); // 다시 데이터 조회
//        }
//        model.addAttribute("stocks", stocks);
//        return "kospi/list";
//    }

//    @GetMapping("/kosdaq/{stockName}")
//    public ResponseEntity<List<KosdaqStockDto>> getStockDetails(@PathVariable("stockName") String stockName) {
//        List<KosdaqStockDto> dto = kosdaqService.findByStockName(stockName);
//        if (dto != null)
//            return ResponseEntity.ok(dto);
//        else
//            return ResponseEntity.notFound().build();
//    }

//    @GetMapping("/kosdaq/data")
//    public String kosdaqStockData() {
//        try {
//            // 크롤링한 데이터 가져오기
//            List<KosdaqStockDto> kosDaqStockList = kosdaqService.getKosDaqStockList();
//
//            // 엔티티로 변환
//            List<KosdaqStockEntity> kosDaqEntities = kosDaqStockList.stream()
//                    .map(kosdaqService::dtoToEntity)
//                    .collect(Collectors.toList());
//
//            // 데이터베이스에 저장 (업데이트와 삽입을 동시에 처리)
//            kosdaqStockRepository.saveAll(kosDaqEntities);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // 리다이렉트
//        return "redirect:/kospi/all";
//    }
}
