package com.example.mp.service;

import com.example.mp.entity.KospiStockEntity;
import com.example.mp.entity.UserEntity;
import com.example.mp.entity.UserStockEntity;
import com.example.mp.repository.KospiStockRepository;
import com.example.mp.repository.UserRepository;
import com.example.mp.repository.UserStockRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional // 메소드 전체를 하나의 트랜잭션으로 관리 트랜잭션이 끝나면 모든 변경 사항이 커밋, 예외 발생 시 롤백.
public class TradingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KospiStockRepository kospiStockRepository;

    @Autowired
    private UserStockRepository userStockRepository;

    public void buyStock(String username, String stockName, int quantity) {
        UserEntity user = userRepository.findByUsername(username);
//                .orElseThrow(() -> new RuntimeException(" 누구? "));
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
//        KospiStockEntity stock = kospiStockRepository.findById(stockName).orElseThrow(() -> new RuntimeException(" 무엇? "));
        // 코스피 정보가 userStock과 직접적인 테이블 연결이 없지만 같이 매칭될 수 있는 부분.
        List<KospiStockEntity> stocks = kospiStockRepository.findByStockName(stockName);
        if (stocks.isEmpty()) {
            throw new RuntimeException("주식이 존재하지 않습니다: " + stockName);
        }

        // stocks 의 0 번째 인 이유는 기본키의 stockName 부분을 가져왔기 때문에 중복되지않는 하나의 값만 나옴 = 0번째가 내가 원하는 주식 값.
        KospiStockEntity stock = stocks.get(0); // 첫 번째 주식 선택
        String a = stock.getPrice().replaceAll("[^0-9]", "");
//        double price = Double.parseDouble(stock.getPrice()); // 가격 가져오기
        double price = Double.parseDouble(a);
        double totalPrice = price * quantity;

        if (user.getCoin() < totalPrice) {
            throw new RuntimeException("잔액 부족");
        }

        user.setCoin(user.getCoin() - (int) totalPrice);
        userRepository.save(user);

        // 사용자 보유 주식 업데이트
        UserStockEntity userStock = userStockRepository.findByStockNameAndUser(stockName, user);
        if (userStock == null) {
            userStock = new UserStockEntity();
            userStock.setUser(user); // TODO. 데이터베이스에 구매한 주식 정보가 저장이 안되었는데 여기서 값이 안들어옴 밑에는 잘 들어옴.
            userStock.setStockName(stockName);
            userStock.setQuantity(quantity);
            userStock.setPricePerShare(price); // 주식 당 가격 설정
            userStock.setAveragePrice(price); // 평균 가격 설정 (처음 구매 시)
            userStock.setStockType("kospi");
        } else {
            // 평균 가격 업데이트
            double totalCost = (userStock.getAveragePrice() * userStock.getQuantity()) + (price * quantity);
            userStock.setQuantity(userStock.getQuantity() + quantity);
            userStock.setAveragePrice(totalCost / userStock.getQuantity()); // 새로운 평균 가격 계산
        }
        userStockRepository.save(userStock);
    }

    public void sellStock(String username, String stockName, int quantity) {
        UserEntity user = userRepository.findByUsername(username);
//                .orElseThrow(() -> new RuntimeException(" 누구? "));
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        UserStockEntity userStock = userStockRepository.findByStockNameAndUser(stockName, user);

        if (userStock == null || userStock.getQuantity() < quantity) {
            throw new RuntimeException(" 바이 ");
        }

        KospiStockEntity stock = kospiStockRepository.findById(stockName).orElseThrow(() -> new RuntimeException("주식 정보 오류"));
//        double price = Double.parseDouble(stock.getPrice());
//        double totalPrice = price * quantity;

        String a = stock.getPrice().replaceAll("[^0-9]", "");
//        double price = Double.parseDouble(stock.getPrice()); // 가격 가져오기
        double price = Double.parseDouble(a);
        double totalPrice = price * quantity;

        user.setCoin(user.getCoin() + (int) totalPrice);
        userRepository.save(user);

        userStock.setQuantity(userStock.getQuantity() - quantity);
        if (userStock.getQuantity() == 0) {
            userStockRepository.delete(userStock);
        } else {
            userStockRepository.save(userStock);
        }
    }
}
