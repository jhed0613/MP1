package com.example.mp.dto;

import com.example.mp.entity.BoardEntity;
import com.example.mp.entity.BoardFileEntity;
import com.example.mp.entity.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

@Data
public class BoardDto {
    private int boardIdx;
    private String title;
    private String content;
    private int hitCnt;
    private String createTime;
    private String updateTime;
    private String createdUsername;
//    private Long userId;  // User의 ID만 포함
    private List<BoardFileDto> fileInfoList;

}
