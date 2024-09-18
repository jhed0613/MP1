package com.example.mp.security;

import com.example.mp.common.JwtUtils;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;

import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.crypto.spec.SecretKeySpec;

@Component
@Slf4j
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private Environment env;
    @Autowired
    private JwtUtils jwtUtils;


    // 이 부분 때문에 SecurityContextHolder.getContext().getAuthentication().getName() 로 현재 로그인된 사용자 정보 받아올 수 있음 ( 캐시되어있어 효율 굳 )
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());

        // 로그인 성공 후 수행할 작업들을 기술
        // 예) 데이터베이스에 접속 로그를 기록, 알림 이메일을 발송, ...
        String jwtToken = jwtUtils.generateToken(userEntity);
        log.error(">>>>>>>>>>>" + jwtToken);

        // 세션에 사용자 정보를 저장
        request.getSession().setAttribute("user", userEntity);

        // 응답 헤더에 생성한 토큰을 설정 ( 로그인하면 개발자도구 네트워크 - 로그인 프록에 토큰 생성 )
        response.setHeader("token", jwtToken);

        // 리다이렉트
        // TODO. 토큰 형식으로 로그인할 때 주석처리 안하면 토큰값을 받아오기 전에 홈으로 리다이렉팅 하기 때문에 주석처리 해줘야 함.
//         response.sendRedirect("/home");
    }

    // TODO. 토큰 생성 메서드 추가 시큐리티 설정에서 사용할 때는 필요없지만 로그인 컨트롤러에서 사용하기 위해 생성 ( 익명 사용자인 것 같아서 시도 중 )
    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        UserEntity userEntity = userRepository.findByUsername(userDetails.getUsername());
        return jwtUtils.generateToken(userEntity);
    }
}

