package com.example.mp.controller;

import com.example.mp.entity.UserEntity;
import com.example.mp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
//@RequestMapping("/home")
//@CrossOrigin(origins="http://localhost:3000", allowedHeaders="GET")
public class HomeController {
    @Autowired
    private UserService userService;
    @GetMapping("/home")
    public ResponseEntity<?> home() {
        log.info("Accessed the Home page");

        UserEntity user = userService.getCurrentUser(); // 현재 사용자 정보 가져오기
        return ResponseEntity.ok(Map.of(
                "user", Map.of(
                        "username", user.getUsername(),
                        "coins", user.getCoin(), // coins 필드가 있다면 추가
                        "Role", user.getRole()
                )
        ));
    }

}
