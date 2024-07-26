package com.example.mp.dto;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class BoardFileDto {
    private int id;
    private int boardIdx;
    private String fileName;
    private String filePath;
    private String fileSize;
    private LocalDateTime createTime;
}