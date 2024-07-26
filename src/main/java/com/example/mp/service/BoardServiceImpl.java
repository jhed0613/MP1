package com.example.mp.service;

import com.example.mp.entity.BoardEntity;
import com.example.mp.entity.BoardFileEntity;
import com.example.mp.repository.BoardRepository;
import com.example.mp.repository.UserRepository;
import com.example.mp.common.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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


    @Override
    public List<BoardEntity> selectBoardList() {
        return boardRepository.findAllByOrderByBoardIdxDesc();
    }

    @Override
    public void insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception {
        // FileUtils 클래스에 parseFileInfo(request) 메서드를 추가
        // @OneToMany 연관관계를 설정했기 때문에 첨부파일에 게시판 번호를 따로 지정하지 않아도 자동으로 설정
        List<BoardFileEntity> list = fileUtils.parseFileInfo(request);
        if (list != null) {
            for (BoardFileEntity file : list) {
                file.setBoard(boardEntity);
            }
            boardEntity.setFileInfoList(list);
        }
        // TODO. 로그인한 사용자 계정으로 변경
//        boardEntity.setCreatorId("tester");

        // 리포지터의 save 메서드는 insert와 update 두 가지 역할을 수행
        boardRepository.save(boardEntity);
    }

//    @Override
//    public void insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request, String loggedInUserId) throws Exception {
//        // FileUtils 클래스에 parseFileInfo(request) 메서드를 추가
//        // @OneToMany 연관관계를 설정했기 때문에 첨부파일에 게시판 번호를 따로 지정하지 않아도 자동으로 설정
//        List<BoardFileEntity> list = fileUtils.parseFileInfo(request);
//        if (list != null) {
//            for (BoardFileEntity file : list) {
//                file.setBoard(boardEntity);
//            }
//            boardEntity.setFileInfoList(list);
//        }
//
//        // 로그인한 사용자 정보로 UserEntity 객체 조회
//        UserEntity user = userRepository.findById(loggedInUserId).orElseThrow(() -> new RuntimeException("User not found"));
//
//        // 게시판 작성자 설정
//        boardEntity.setUser(user);
//
//        // 리포지터리의 save 메서드는 insert와 update 두 가지 역할을 수행
//        boardRepository.save(boardEntity);
//    }

    @Override
    public void updateBoard(BoardEntity boardEntity) throws Exception {
        // TODO. 로그인한 사용자 계정으로 변경
//        boardEntity.setCreatorId("tester");

        // 리포지터리의 save 메서드는 insert와 update 두 가지 역할을 수행
        boardRepository.save(boardEntity);
    }

    @Override
    public BoardEntity selectBoardDetail(int boardIdx) throws Exception {
        Optional<BoardEntity> optional = boardRepository.findById(boardIdx);
        if (optional.isPresent()) {         // 존재하면 ~
            BoardEntity boardEntity = optional.get();
            boardEntity.setHitCnt(boardEntity.getHitCnt() + 1);
            boardRepository.save(boardEntity);
            return boardEntity;
        } else {
            throw new Exception("일치하는 정보가 없습니다");
        }
    }

    @Override
    public void deleteBoard(int boardIdx) {
        boardRepository.deleteById(boardIdx);
    }

    @Override
    public BoardFileEntity selectBoardFileInfo(int idx, int boardIdx) {
        return boardRepository.findBoardFile(idx, boardIdx);
    }
}