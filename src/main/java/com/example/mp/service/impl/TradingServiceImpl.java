package com.example.mp.service.impl;

import com.example.mp.dto.UserStockDto;
import com.example.mp.entity.KosdaqStockEntity;
import com.example.mp.entity.KospiStockEntity;
import com.example.mp.entity.UserEntity;
import com.example.mp.entity.UserStockEntity;
import com.example.mp.mapper.UserStockMapper;
import com.example.mp.repository.KosdaqStockRepository;
import com.example.mp.repository.KospiStockRepository;
import com.example.mp.repository.UserRepository;
import com.example.mp.repository.UserStockRepository;
import com.example.mp.service.TradingService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional // 메소드 전체를 하나의 트랜잭션으로 관리 트랜잭션이 끝나면 모든 변경 사항이 커밋, 예외 발생 시 롤백.
public class TradingServiceImpl implements TradingService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KospiStockRepository kospiStockRepository;

    @Autowired
    private KosdaqStockRepository kosdaqStockRepository;

    @Autowired
    private UserStockRepository userStockRepository;

    @Autowired
    private UserStockMapper userStockMapper;

    @Override
    public void buyStock(String username, String stockName, int quantity, String stockType) {
        UserEntity user = userRepository.findByUsername(username);

        if (quantity == 0){
            throw new NumberFormatException("수량을 입력하세요.");
        }
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        double price;

        // stockType 을 기준으로 코스피인지 코스닥인지 나누어 처리
        if (stockType.equalsIgnoreCase("kospi")) {
            // 코스피 정보가 userStock과 직접적인 테이블 연결이 없지만 같이 매칭될 수 있는 부분.
            List<KospiStockEntity> stocks = kospiStockRepository.findByStockName(stockName);
            if (stocks.isEmpty()) {
                throw new RuntimeException("코스피 주식이 존재하지 않습니다: " + stockName);
            }
            // stocks 의 0 번째 인 이유는 기본키의 stockName 부분을 가져왔기 때문에 중복되지않는 하나의 값만 나옴 = 0번째가 내가 원하는 주식 값.
            KospiStockEntity stock = stocks.get(0);
            price = extractPrice(stock.getPrice());
        } else if (stockType.equalsIgnoreCase("kosdaq")) {
            List<KosdaqStockEntity> stocks = kosdaqStockRepository.findByStockName(stockName);
            if (stocks.isEmpty()) {
                throw new RuntimeException("코스닥 주식이 존재하지 않습니다: " + stockName);
            }
            KosdaqStockEntity stock = stocks.get(0);
            price = extractPrice(stock.getPrice());
        } else {
            throw new RuntimeException("잘못된 시장 타입입니다: " + stockType);
        }

        double totalPrice = price * quantity;

        if (user.getCoin() < totalPrice) {
            throw new NumberFormatException("잔액 부족");
        }

        user.setCoin(user.getCoin() - (int) totalPrice);
        userRepository.save(user);

        // 사용자 보유 주식 업데이트
        UserStockEntity userStock = userStockRepository.findByStockNameAndUser(stockName, user);
        if (userStock == null) {
            userStock = new UserStockEntity();
            userStock.setUser(user);
            userStock.setStockName(stockName);
            userStock.setQuantity(quantity);
            userStock.setPricePerShare(price);
            userStock.setAveragePrice(price);
            userStock.setTotalPrice(price * quantity);
            userStock.setStockType(stockType.toLowerCase()); // kospi 또는 kosdaq
        } else {
            double totalCost = (userStock.getAveragePrice() * userStock.getQuantity()) + (price * quantity);
            userStock.setQuantity(userStock.getQuantity() + quantity);
            userStock.setAveragePrice(totalCost / userStock.getQuantity());  // 새로운 평균 가격 계산
            userStock.setTotalPrice(totalCost);
        }
        userStockRepository.save(userStock);
    }

    private double extractPrice(String priceString) {
        return Double.parseDouble(priceString.replaceAll("[^0-9]", ""));
    }

    @Override
    public void sellStock(String username, String stockName, int quantity, String stockType) {
        UserEntity user = userRepository.findByUsername(username);

        if (quantity == 0){
            throw new NumberFormatException("수량을 입력하세요.");
        }
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        UserStockEntity userStock = userStockRepository.findByStockNameAndUser(stockName, user);

        if (userStock == null) {
            throw new RuntimeException("사용자가 보유한 주식 없음");
        }
        else if (userStock.getQuantity() < quantity){
            throw new RuntimeException("보유 주식 개수보다 선택 개수가 많음");
        }

        double price;

        // stockType 을 기준으로 코스피인지 코스닥인지 나누어 처리
        if (stockType.equalsIgnoreCase("kospi")) {
            // 코스피 정보가 userStock과 직접적인 테이블 연결이 없지만 같이 매칭될 수 있는 부분.
            List<KospiStockEntity> stocks = kospiStockRepository.findByStockName(stockName);
            if (stocks.isEmpty()) {
                throw new RuntimeException("코스피 주식이 존재하지 않습니다: " + stockName);
            }
            // stocks 의 0 번째 인 이유는 기본키의 stockName 부분을 가져왔기 때문에 중복되지않는 하나의 값만 나옴 = 0번째가 내가 원하는 주식 값.
            KospiStockEntity stock = stocks.get(0);
            price = extractPrice(stock.getPrice());
        } else if (stockType.equalsIgnoreCase("kosdaq")) {
            List<KosdaqStockEntity> stocks = kosdaqStockRepository.findByStockName(stockName);
            if (stocks.isEmpty()) {
                throw new RuntimeException("코스닥 주식이 존재하지 않습니다: " + stockName);
            }
            KosdaqStockEntity stock = stocks.get(0);
            price = extractPrice(stock.getPrice());
        } else {
            throw new RuntimeException("잘못된 시장 타입입니다: " + stockType);
        }

        double totalPrice = price * quantity;

        user.setCoin(user.getCoin() + (int) totalPrice);
        userRepository.save(user);

        double averagePriceBeforeSale = userStock.getAveragePrice();
        double totalCostBeforeSale = averagePriceBeforeSale * userStock.getQuantity();
        double totalCostAfterSale = totalCostBeforeSale - (totalPrice);

        if (userStock.getQuantity() - quantity > 0) {
            double newAveragePrice = totalCostAfterSale / (userStock.getQuantity() - quantity);
            userStock.setAveragePrice(newAveragePrice);
            userStock.setTotalPrice(totalCostAfterSale);
            userStock.setQuantity(userStock.getQuantity() - quantity);
        }
        else {
            userStockRepository.delete(userStock);
        }
    }
    @Override
    public List<UserStockDto> getUserStockList(long userId) {
        List<UserStockEntity> userStockEntities = userStockRepository.findByUserId(userId);

        return userStockEntities.stream().map(userStock -> {
            UserStockDto userStockDto = userStockMapper.toDto(userStock);

            double realTimePrice = 0;
            if (userStock.getStockType().equalsIgnoreCase("kospi")) {
                KospiStockEntity stock = kospiStockRepository.findByStockName(userStock.getStockName()).get(0);
                realTimePrice = extractPrice(stock.getPrice());
            } else if (userStock.getStockType().equalsIgnoreCase("kosdaq")) {
                KosdaqStockEntity stock = kosdaqStockRepository.findByStockName(userStock.getStockName()).get(0);
                realTimePrice = extractPrice(stock.getPrice());
            }

            double totalCurrentValue = userStock.getQuantity() * realTimePrice;
            double profit = totalCurrentValue - userStock.getTotalPrice(); // 순이익 계산

            userStockDto.setTotalCurrentValue(totalCurrentValue);
            userStockDto.setProfit(profit);

            return userStockDto;
        }).collect(Collectors.toList());
    }
}
