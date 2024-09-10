package com.example.mp.controller;


import com.example.mp.dto.BuyRequest;
import com.example.mp.service.impl.TradingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trading")
public class TradingController {

    @Autowired
    private TradingServiceImpl tradingService;

    @PostMapping("/buy")
    public ResponseEntity<String> buyStock(@RequestBody BuyRequest buyRequest) {
        try {
            String username = buyRequest.getUsername();
            String stockName = buyRequest.getStockName();

            int quantity = buyRequest.getQuantity();
            String stockType = buyRequest.getStockType();

            // 주식 구매 처리
            tradingService.buyStock(username, stockName, quantity, stockType);

            return ResponseEntity.ok("매수 성공");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("잘못된 숫자 형식: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("매수 실패: " + e.getMessage());
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellStock(@RequestBody BuyRequest buyRequest) {
        try {
            String username = buyRequest.getUsername();
            String stockName = buyRequest.getStockName();
            int quantity = buyRequest.getQuantity();
            String stockType = buyRequest.getStockType();

            tradingService.sellStock(username, stockName, quantity, stockType);

            return ResponseEntity.ok(" 매도 성공 ");
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("잘못된 숫자 형식: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("매도 실패: " + e.getMessage());
        }
    }
}