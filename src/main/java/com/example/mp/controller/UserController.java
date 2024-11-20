package com.example.mp.controller;


import com.example.mp.dto.UserStockDto;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import com.example.mp.service.TradingService;
import com.example.mp.service.UserService;
import com.example.mp.dto.JoinDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TradingService tradingService;
    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUserInfo(Principal principal) {
        // 현재 인증된 유저를 가져옵니다
        UserEntity userEntity = userRepository.findByUsername(principal.getName());
        return ResponseEntity.ok(userEntity);
    }

    @GetMapping("api/mypage/{userId}")
    public List<UserStockDto> getUserStockInfo(@PathVariable("userId") long userId){
        return tradingService.getUserStockList(userId);
    }
}

