package com.example.mp.service;

import com.example.mp.dto.BoardDto;
import com.example.mp.entity.BoardEntity;
import com.example.mp.entity.BoardFileEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import java.util.List;

public interface BoardService {
    List<BoardEntity> selectBoardList();
    // 등록과 수정을 분리
//    void insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request, String loggedInUserId) throws Exception;
    void insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception;
//    void updateBoard(BoardEntity boardEntity) throws Exception;
    void updateBoard(int boardIdx, BoardDto boardDto) throws Exception;
    void deleteBoard(int boardIdx);
    BoardFileEntity selectBoardFileInfo(int idx, int boardIdx);
    BoardEntity selectBoardDetail(int boardIdx) throws Exception;
    BoardDto entityToDto (BoardEntity boardEntity);
    List<BoardFileEntity> getFilesByBoardId(int boardIdx);
}
