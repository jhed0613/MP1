package com.example.mp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String password;
    private String userName;
    private String email;
    private int coin;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}

