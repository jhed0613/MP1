package com.example.mp.controller;


import com.example.mp.dto.KosdakStockDto;
import com.example.mp.dto.KospiStockDto;
import com.example.mp.entity.KosdakStockEntity;
import com.example.mp.entity.KospiStockEntity;
import com.example.mp.repository.KosdakStockRepository;
import com.example.mp.service.KosdakService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
public class KosdakController {
    @Autowired
    private KosdakService kosdakService;
    @Autowired
    private KosdakStockRepository kosdakStockRepository;

    @GetMapping("/kosdak")
    public List<KosdakStockDto> getKosDakStockList(HttpServletRequest request) {
        return kosdakService.getKosDakStockList();
    }
    @GetMapping("/kosdak/list")
    public String listKosdakStocks(Model model) {
        List<KosdakStockEntity> stocks = kosdakStockRepository.findAll();
        if (stocks.isEmpty()) {
            // 데이터가 없으면 크롤링하여 데이터베이스에 저장
            kosdakService.updateKosdakData(); // 크롤링 및 저장 메서드 호출
            stocks = kosdakStockRepository.findAll(); // 다시 데이터 조회
        }
        model.addAttribute("stocks", stocks);
        return "kospi/list";
    }

//    @GetMapping("/kosdak/{stockName}")
//    public ResponseEntity<List<KosdakStockDto>> getStockDetails(@PathVariable("stockName") String stockName) {
//        List<KosdakStockDto> dto = kosdakService.findByStockName(stockName);
//        if (dto != null)
//            return ResponseEntity.ok(dto);
//        else
//            return ResponseEntity.notFound().build();
//    }

    @GetMapping("/kosdak/data")
    public String kosdakStockData() {
        try {
            // 크롤링한 데이터 가져오기
            List<KosdakStockDto> kosDakStockList = kosdakService.getKosDakStockList();

            // 엔티티로 변환
            List<KosdakStockEntity> kosDakEntities = kosDakStockList.stream()
                    .map(kosdakService::dtoToEntity)
                    .collect(Collectors.toList());

            // 데이터베이스에 저장 (업데이트와 삽입을 동시에 처리)
            kosdakStockRepository.saveAll(kosDakEntities);

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 리다이렉트
        return "redirect:/kospi/all";
    }
//    @GetMapping("/search/kosdak")
//    public String searchStocks(@RequestParam(name = "stockName", required = false) String stockName, Model model) {
//        if (stockName == null || stockName.isEmpty()) {
//            model.addAttribute("stocks", new ArrayList<KospiStockDto>());
//        } else {
//            List<KosdakStockDto> filteredStocks = kosdakStockRepository.findByStockName(stockName);
//            model.addAttribute("stocks", filteredStocks);
//        }
//        return "search";
//    }
}
