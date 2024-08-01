package com.example.mp.service;

import com.example.mp.dto.CustomUserDetail;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static org.hibernate.query.sqm.tree.SqmNode.log;

@Service
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    // TODO. 로그인 시도하면 이 쪽으로 데이터 넘어옴
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUsername(username);
        log.error("@@@@@@@@@@@" + username);
        log.error("@@@@@!@!@@!@!@!@" +userEntity.getRole()); // ROLE_USER 로 넘어옴.

        if (userEntity == null) {
            throw new UsernameNotFoundException("등록된 사용자가 없습니다.");
        }

        return new CustomUserDetail(userEntity);
    }
}

