package com.example.mp.repository;



import com.example.mp.entity.KospiStockEntity;
import com.example.mp.entity.UserEntity;
import com.example.mp.entity.UserStockEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStockRepository extends JpaRepository<UserStockEntity, Long> {
    UserStockEntity findByStockNameAndUser(String stockName, UserEntity user);
    UserStockEntity findByStockNameAndStockTypeAndUser(String stockName, String stockType, UserEntity user);

}

