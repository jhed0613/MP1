package com.example.mp.security;

import com.example.mp.common.JwtUtils;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

//    @Autowired
//    private UserRepository repository;
    private final UserRepository repository;

    public JwtRequestFilter (UserRepository userRepository){
        this.repository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // /, /login, /loginProc 에 대해서는 헤더 검증을 생략
        String uri = request.getRequestURI();
        if (uri.equals("/home") || uri.equals("/login") || uri.equals("/loginProc") || uri.equals("/join") || uri.equals("/joinProc") || uri.contains("/stocks")) {
            filterChain.doFilter(request, response);
            return;
        }

        String jwtToken = null;
        String subject = null;

        // Authorization 요청 헤더 존재 여부를 확인하고, 헤더 정보를 추출
        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        // Authorization 요청 헤더의 값이 Bearer 문자로 시작하는 확인 후 토큰값을 추출
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);	// "Bearer " 이후의 모든 내용
            subject = jwtUtils.getSubjectFromToken(jwtToken);
        } else {
            log.error("Authorization 헤더 누락 또는 토큰 형식 오류");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token");
            response.getWriter().flush();
            return;
        }

        UserEntity userEntity = repository.findByUsername(subject);
        if (!jwtUtils.validateToken(jwtToken, userEntity)) {
            log.error("사용자 정보가 일치하지 않습니다.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT token");
            response.getWriter().flush();
            return;
        }

        filterChain.doFilter(request, response);
    }
}
