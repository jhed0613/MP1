package com.example.mp.controller;


import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import com.example.mp.service.UserService;
import com.example.mp.dto.JoinDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.Duration;
import java.util.Collections;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/user")
    public ResponseEntity<UserEntity> getUserInfo(Principal principal) {
        // 현재 인증된 유저를 가져옵니다
        UserEntity userEntity = userRepository.findByUsername(principal.getName());
        return ResponseEntity.ok(userEntity);
    }
}

