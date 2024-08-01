package com.example.mp.service;

import com.example.mp.dto.CustomUserDetail;
import com.example.mp.dto.JoinDto;
import com.example.mp.dto.KospiStockDto;
import com.example.mp.dto.UserDto;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@Slf4j
//@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository; // UserRepository는 JPA를 통해 UserEntity에 접근

    // 현재 로그인한 사용자 정보를 가져오는 메서드 (예시)
    public UserEntity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new RuntimeException("Authentication object is null");
        }

        log.info("Authentication object: " + authentication);

        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
            UserEntity user = customUserDetail.getUserEntity();
            log.info("Current user: " + user.getUsername());
            return user;
        }

        throw new RuntimeException("Current user not found");
    }
}

