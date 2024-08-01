package com.example.mp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "UserStock")
@Data
public class UserStockEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // 실제 유저 테이블은 기본키가 ID 지만 JPA 에서는 테이블명_기본키 조합을 사용함
    private UserEntity user;

    private String stockName; // 주식 이름
    private String stockType; // 코스피, 코스닥
    private int quantity; // 보유 주식 수량
    private double pricePerShare; // 주식 당 가격
    private double averagePrice; // 평균 구매 가격
    private LocalDateTime purchaseDate; // 날짜

    // 현재 주식 가격을 기반으로 총 가치를 계산하는 메서드
    public double getTotalValue() {
        return quantity * pricePerShare;
    }

    // 가격 변화 및 퍼센트를 계산하는 메서드
    public StockPriceChange getPriceChange(double currentPrice) {
        double changeAmount = currentPrice - averagePrice;
        double percentageChange = (changeAmount / averagePrice) * 100;

        return new StockPriceChange(changeAmount, percentageChange);
    }

    // 가격 변화 정보를 담는 클래스
    public static class StockPriceChange {
        private double changeAmount; // 변화량
        private double percentage; // 변화 퍼센트

        public StockPriceChange(double changeAmount, double percentage) {
            this.changeAmount = changeAmount;
            this.percentage = percentage;
        }

        public double getChangeAmount() {
            return changeAmount;
        }

        public double getPercentage() {
            return percentage;
        }
    }
}


