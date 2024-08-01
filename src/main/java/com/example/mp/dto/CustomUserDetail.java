package com.example.mp.dto;

import com.example.mp.entity.UserEntity;
import com.example.mp.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CustomUserDetail implements UserDetails {
    private UserEntity userEntity;
    private UserRepository userRepository;

    public CustomUserDetail(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // 권한 정보를 포함하여 반환
//        return Collections.singletonList(new SimpleGrantedAuthority(userEntity.getRole()));
//    }
    public UserEntity getUserEntity() {
        return userEntity;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return userEntity.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return userEntity.getPassword();
    }

    @Override
    public String getUsername() {
        return userEntity.getUsername();
    }

//    @Override
//    public boolean isAccountNonExpired() {
//        return true; // 실제로는 계정 만료 여부를 체크해야 함
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true; // 실제로는 계정 잠금 여부를 체크해야 함
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true; // 실제로는 자격 증명 만료 여부를 체크해야 함
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true; // 실제로는 계정 활성화 여부를 체크해야 함
//    }
//    public UserEntity getUserEntity() {
//        return userEntity;
//    }
}

