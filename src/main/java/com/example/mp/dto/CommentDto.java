package com.example.mp.dto;

import com.example.mp.entity.BoardEntity;
import com.example.mp.entity.UserEntity;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Integer commentId;
    private int boardIdx;  // BoardEntity 대신 boardIdx
    private Long userId;  // UserEntity 대신 userId
    private String content;
    private LocalDateTime createTime;  // LocalDateTime으로 변경
}