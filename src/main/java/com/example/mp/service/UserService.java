package com.example.mp.service;

import com.example.mp.dto.JoinDto;
import com.example.mp.dto.KospiStockDto;
import com.example.mp.dto.UserDto;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

public interface UserService {
    UserEntity getCurrentUser();

//    void purchaseStock(UserEntity user, KospiStockDto stock);
//    UserEntity findById(Long userId);
//    Long save(AddUserRequest dto);
//    boolean registerUser(String username, String password);
//    Optional<String> authenticateUser(String username, String password);
}