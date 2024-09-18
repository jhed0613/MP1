package com.example.mp.service.impl;

import com.example.mp.common.JwtUtils;
import com.example.mp.dto.BoardDto;
import com.example.mp.entity.BoardEntity;
import com.example.mp.entity.BoardFileEntity;
import com.example.mp.entity.UserEntity;
import com.example.mp.repository.BoardFileRepository;
import com.example.mp.repository.BoardRepository;
import com.example.mp.repository.UserRepository;
import com.example.mp.common.FileUtils;
import com.example.mp.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private FileUtils fileUtils;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardFileRepository boardFileRepository;

    public List<BoardFileEntity> getFilesByBoardId(int boardIdx) {
        return boardFileRepository.findByBoard_BoardIdx(boardIdx);
    }

    @Override
    public List<BoardEntity> selectBoardList() {
        return boardRepository.findAllByOrderByBoardIdxDesc();
    }

    public void insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        UserEntity userEntity = userRepository.findByUsername(username);
        if (userEntity != null) {
            boardEntity.setCreatedUsername(userEntity.getUsername());
        } else {
            throw new Exception("User not found");
        }

        // 파일 처리
        List<BoardFileEntity> fileInfoList = fileUtils.parseFileInfo(request);

        // 게시판 엔티티에 파일 정보 연결
        if (fileInfoList != null && !fileInfoList.isEmpty()) {
            for (BoardFileEntity boardFile : fileInfoList) {
                boardFile.setBoard(boardEntity); // 관련된 BoardEntity 설정
            }
            boardEntity.setFileInfoList(fileInfoList); // 게시판 엔티티에 파일 리스트 설정
        }

        // 게시판 엔티티 저장
        boardRepository.save(boardEntity);

        // 추가: 파일 정보도 저장 (CascadeType.ALL이 설정된 경우에는 필요 없음)
        for (BoardFileEntity boardFile : fileInfoList) {
            boardFileRepository.save(boardFile); // 파일 정보 저장 (Cascade 설정에 따라 선택적)
        }
    }

    @Override
    public void updateBoard(int boardIdx, BoardDto boardDto) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        BoardEntity boardEntity = boardRepository.findById(boardIdx)
                .orElseThrow(() -> new Exception("게시글을 찾을 수 없습니다."));

//        UserEntity userEntity = userRepository.findByUsername(username);
        if (username.equals(boardEntity.getCreatedUsername())) {
            // DTO에서 받은 값으로 기존 엔티티 업데이트
            boardEntity.setTitle(boardDto.getTitle());
            boardEntity.setContent(boardDto.getContent());
            boardEntity.setUpdateTime(LocalDateTime.now());
            // 필요한 다른 필드들도 업데이트
        } else {
            throw new Exception("User not found");
        }
        // 업데이트된 엔티티를 저장
        boardRepository.save(boardEntity);
    }

    @Override
    public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
        Optional<BoardEntity> optional = boardRepository.findById(boardIdx);
        if (optional.isPresent()) {         // 존재하면 ~
            BoardEntity boardEntity = optional.get();
            boardEntity.setHitCnt(boardEntity.getHitCnt() + 1);

            List<BoardFileEntity> fileInfoList = boardFileRepository.findByBoard(boardEntity);
            boardEntity.setFileInfoList(fileInfoList);

            boardRepository.save(boardEntity);
            return boardEntity;
        } else {
            throw new Exception("일치하는 정보가 없습니다");
        }
    }

    @Override
    public void deleteBoard(int boardIdx, HttpServletRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<BoardEntity> boardEntity = boardRepository.findByBoardIdxAndCreatedUsername(boardIdx, username);

        if (boardEntity.isPresent()) {
            boardRepository.delete(boardEntity.get());
        } else {
            throw new RuntimeException("해당 게시물을 삭제할 권한이 없습니다.");
        }
    }


    @Override
    public BoardFileEntity selectBoardFileInfo(int idx, int boardIdx) {
        return boardRepository.findBoardFile(idx, boardIdx);
    }

    @Override
    public BoardDto entityToDto(BoardEntity boardEntity) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardIdx(boardEntity.getBoardIdx());
        boardDto.setTitle(boardEntity.getTitle());
        boardDto.setContent(boardEntity.getContent());
        boardDto.setHitCnt(boardEntity.getHitCnt());
        boardDto.setCreateTime(boardEntity.getCreateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)); // LocalDateTime을 문자열로 변환
        return boardDto;
    }
}