package com.example.mp.controller;

import com.example.mp.dto.UserDto;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import com.example.mp.security.CustomAuthenticationSuccessHandler;
import com.example.mp.service.BoardService;
import com.example.mp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private CustomAuthenticationSuccessHandler successHandler;



//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
//        try {
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password)
//            );
//            SecurityContextHolder.getContext().setAuthentication(authentication);
////            String token = generateToken(authentication);
////            return ResponseEntity.ok().header("token", token).build();
//            return ResponseEntity.ok().build();
//        } catch (AuthenticationException e) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//        }
//    }

    // TODO. 시큐리티 설정에서 원래 자동으로 보내주는데 그렇게 하면 토큰은 잘 받아오는데 올바른 토큰으로도 사용자를 읽지 못하는 오류가 발생해서 이렇게 했음 ( 다시 수정 )
    // 사용자 인증
//    @PostMapping("/loginProc")
//    public ResponseEntity<?> loginProc(@RequestParam String username, @RequestParam String password) {
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(username, password));
//
//        // 인증이 성공하면 SecurityContext에 저장
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        log.error(">>>>>>>>>>>>" + username);
//
//        // 인증 성공 후 토큰 생성
//        String token = successHandler.generateToken(authentication);
//
//        return ResponseEntity.ok().header("token",token).body("로그인 성공");
//    }

//    @PostMapping("/loginProc")
//    public ResponseEntity<?> loginProc(@RequestParam String username, @RequestParam String password) {
//        try {
//            // 사용자 인증
//            Authentication authentication = authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(username, password));
//
//            // 인증이 성공하면 SecurityContext에 저장
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            log.error(">>>>>>>>>>>>" + username);
//
//            // 인증 성공 후 토큰 생성
//            String token = successHandler.generateToken(authentication);
//
//            // 사용자 역할 가져오기
//            String roles = authentication.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .collect(Collectors.joining(", "));
//
//            // 응답 데이터 구성
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "로그인 성공");
//            response.put("token", token);
//            response.put("username", username);
//            response.put("roles", roles);
//
//            log.error("<<<<<<<<<<<" + username);
//            log.error("<<<<<<<<<<<" + roles);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            log.error("로그인 실패: " + e.getMessage());
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
//        }
//    }


    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/home"; // SecurityConfiguration에서 설정한 logoutSuccessUrl 값 보다 우선
    }

}

