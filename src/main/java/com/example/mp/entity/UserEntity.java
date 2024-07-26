package com.example.mp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Table(name = "User")
@Data
public class UserEntity {
    @Id
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(name = "username", nullable = false)
    private String userName;

    @Column(nullable = false)
    private String email;

    private int points;

    @Column(name = "created_at")
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updateTime;
}
