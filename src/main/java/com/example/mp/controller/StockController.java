package com.example.mp.controller;

import com.example.mp.dto.KorStockDto;
import com.example.mp.service.StockService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/kospi/all")
    public ModelAndView openBoardList(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("/kospiStockList");

        List<KorStockDto> stockDto = stockService.getKosPiStockList();
        mv.addObject("stocks", stockDto);

        return mv;
    }

    @GetMapping("/kosdak/all")
    public List<KorStockDto> getKosDakStockList(HttpServletRequest request) {
        return stockService.getKosDakStockList();
    }

//    @GetMapping("/search")
//    public String searchStocks(@RequestParam("stockName") String stockName, Model model) {
//        List<KorStockDto> filteredStocks = stockService.findByStockName(stockName);
//        model.addAttribute("stocks", filteredStocks);
//        return "search";
//    }

    @GetMapping("/search")
    public String searchStocks(@RequestParam(name = "stockName", required = false) String stockName, Model model) {
        if (stockName == null || stockName.isEmpty()) {
            model.addAttribute("stocks", new ArrayList<KorStockDto>());
        } else {
            List<KorStockDto> filteredStocks = stockService.findByStockName(stockName);
            model.addAttribute("stocks", filteredStocks);
        }
        return "search";
    }
}

