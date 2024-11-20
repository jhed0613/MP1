package com.example.mp.controller;

import com.example.mp.auth.LoginRequest;
import com.example.mp.common.JwtUtils;
import com.example.mp.dto.AuthRequest;
import com.example.mp.dto.AuthResponse;
import com.example.mp.dto.JoinDto;
import com.example.mp.service.JoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class JoinController {
    @Autowired
    JoinService joinService;

    @Autowired
    private JwtUtils jwtUtil;
    @GetMapping("/join")
    public String join() {
        return "/join";
    }

//    @PostMapping("/loginProc")
//    public String login() {
//        return "/home";
//    }

//    @GetMapping("/login")
//    public String loginPage() {
//        return "/login";
//    }
//    @PostMapping("/loginProc")
//    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
//        // 로그인 처리 로직
//        return ResponseEntity.ok().body("Login successful");
//    }

    // 리퀘스트 바디 임포트 잘 못 되어있어서 안 됐음;;
    @PostMapping("/joinProc")
    public ResponseEntity<?> joinProc(@RequestBody JoinDto joinDto) {
        if (joinService.joinProcess(joinDto)) {
            return ResponseEntity.ok().build(); // 성공 시 200 상태 코드 반환
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("실패");
        }
    }
}





