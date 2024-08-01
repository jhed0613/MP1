package com.example.mp.auth;

import com.example.mp.common.JwtUtils;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import com.example.mp.security.CustomAuthenticationSuccessHandler;
import com.example.mp.service.CustomUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class AuthController {
    @Autowired
    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserDetailService userDetailsService;

//    @GetMapping("/loginProc")
//    public String login() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String email = auth.getPrincipal().toString();
//        return email;
//    }
}
