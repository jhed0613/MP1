package com.example.mp.service;

import com.example.mp.dto.BoardDto;
import com.example.mp.entity.BoardEntity;
import com.example.mp.entity.BoardFileEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import java.util.List;

public interface BoardService {
    List<BoardEntity> selectBoardList();
    void insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception;
    void updateBoard(int boardIdx, BoardDto boardDto) throws Exception;
    void deleteBoard(int boardIdx, HttpServletRequest request);
    BoardFileEntity selectBoardFileInfo(int idx, int boardIdx);
    BoardEntity selectBoardDetail(int boardIdx) throws Exception;
    BoardDto entityToDto (BoardEntity boardEntity);
    List<BoardFileEntity> getFilesByBoardId(int boardIdx);
}
