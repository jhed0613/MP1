package com.example.mp.repository;

import com.example.mp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
    boolean existsByUsername(String username);
    UserEntity findByUsername(String username);
//    Optional<UserEntity> findByUsername(String username);

}
