package com.example.mp.controller;

import com.example.mp.entity.BoardEntity;
import com.example.mp.entity.BoardFileEntity;
import com.example.mp.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/jpa")
public class BoardController {

    @Autowired
    private BoardService boardService;

    // 게시판 목록 조회
    // @RequestMapping(value="/board", method=RequestMethod.GET)
    @GetMapping("/board")
    public ModelAndView openBoardList(HttpServletRequest request) throws Exception {
        ModelAndView mv = new ModelAndView("/jpaBoardList");

        // DTO -> Entity 로 변경
        List<BoardEntity> list = boardService.selectBoardList();
        mv.addObject("jpaControllerList", list);

        return mv;
    }

    // 게시판 등록 화면
    @GetMapping("/board/write")
    public String openBoardWrite() throws Exception {
        return "/jpaBoardWrite";
    }

    // 게시판 등록
    @PostMapping("/board/write")
    public String insertBoard(BoardEntity BoardEntity, MultipartHttpServletRequest request, String loggedInUserId) throws Exception {
        boardService.insertBoard(BoardEntity, request);
        return "redirect:/jpa/board";
    }

    // 게시판 상세 조회
    @GetMapping("/board/{boardIdx}")
    public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
        ModelAndView mv = new ModelAndView("/jpaBoardDetail");

        BoardEntity boardEntity = boardService.selectBoardDetail(boardIdx);
        mv.addObject("board", boardEntity);

        return mv;
    }

    // 게시판 수정
    @PutMapping("/board/{boardIdx}")
    public String updateBoard(@PathVariable("boardIdx") int boardIdx, BoardEntity BoardEntity) throws Exception {
        boardService.updateBoard(BoardEntity);
        return "redirect:/jpa/board";
    }

    // 게시판 삭제
    @DeleteMapping("/board/{boardIdx}")
    public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
        return "redirect:/jpa/board";
    }

    // 첨부파일 다운로드
    @GetMapping("/board/file/{boardIdx}/{idx}")
    public void downloadBoardFile(@PathVariable("idx") int idx, @PathVariable("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {
        BoardFileEntity boardFileEntity = boardService.selectBoardFileInfo(idx, boardIdx);
        if (ObjectUtils.isEmpty(boardFileEntity)) {
            return;
        }

        Path path = Paths.get(boardFileEntity.getFilePath());
        byte[] file = Files.readAllBytes(path);

        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(boardFileEntity.getFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");

        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
}


