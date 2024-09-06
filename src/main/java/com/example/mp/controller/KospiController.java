package com.example.mp.controller;

import com.example.mp.dto.KospiStockDto;
import com.example.mp.entity.KospiStockEntity;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.KospiStockRepository;
import com.example.mp.service.KospiService;
import com.example.mp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stocks")
@Slf4j
public class KospiController {

    @Autowired
    private KospiService kospiService;

    @Autowired
    private KospiStockRepository kospiStockRepository;
    @Autowired
    private UserService userService;
    @GetMapping("/kospi")
    public List<KospiStockDto> getKospiStockList(HttpServletRequest request) {
        return kospiService.getKosPiStockList();
    }

//    @GetMapping("/kospi/list")
//    public String listKospiStocks(Model model) {
//        List<KospiStockEntity> stocks = kospiStockRepository.findAll();
//        if (stocks.isEmpty()) {
//            // 데이터가 없으면 크롤링하여 데이터베이스에 저장
//            kospiService.updateKospiData(); // 크롤링 및 저장 메서드 호출
//            stocks = kospiStockRepository.findAll(); // 다시 데이터 조회
//        }
//        model.addAttribute("stocks", stocks);
//        return "kospi/list";
//    }


//    @GetMapping("/kospi/data")
//    public String kospiStockData() {
//        try {
//            // 크롤링한 데이터 가져오기
//            List<KospiStockDto> kosPiStockList = kospiService.getKosPiStockList();
//
//            // 엔티티로 변환
//            List<KospiStockEntity> kosPiEntities = kosPiStockList.stream()
//                    .map(kospiService::dtoToEntity)
//                    .collect(Collectors.toList());
//
//            // 데이터베이스에 저장 (업데이트와 삽입을 동시에 처리)
//            kospiStockRepository.saveAll(kosPiEntities);
//
//            UserEntity user = userService.getCurrentUser(); // 현재 사용자 정보 가져오기
//
//        } catch (Exception e) {
//            // 예외 처리
//            e.printStackTrace();
//        }
//
//        // 리다이렉트
//        return "redirect:/kospi/list";
//    }

//    @GetMapping("/search/kospi")
//    public String searchStocks(@RequestParam(name = "stockName", required = false) String stockName, Model model) {
//        if (stockName == null || stockName.isEmpty()) {
//            model.addAttribute("stocks", new ArrayList<KospiStockDto>());
//        } else {
//            List<KospiStockDto> filteredStocks = kospiStockRepository.findByStockName(stockName);
//            model.addAttribute("stocks", filteredStocks);
//        }
//        return "search";
//    }

    // 권한 없을 떄 역할 찍어본 코드
//         Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////       System.out.println("User authorities: " + authentication.getAuthorities());
//        log.error("User authorities: " + authentication.getAuthorities());
//
//        if (authentication == null || !authentication.isAuthenticated()) {
////            System.out.println("User is not authenticated");
//        log.error("User is not authenticated");
//    }
//
//        if (authentication != null) {
//        log.error("User roles: >>>>>>>>" + authentication.getAuthorities());
//
//        // 사용자 롤을 확인
//        boolean hasAdminRole = authentication.getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
//        boolean hasUserRole = authentication.getAuthorities().stream()
//                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));
//
//        // 롤에 따라 추가적인 로직 작성 가능
//        if (hasAdminRole) {
////                System.out.println("User has ADMIN role");
//            log.error("User has ADMIN role");
//        }
//        if (hasUserRole) {
////                System.out.println("User has USER role");
//            log.error("User has USER role");
//        }
//    }
}

