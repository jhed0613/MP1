package com.example.mp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private String id;
    private String password;
    private String userName;
    private String email;
    private int points;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

