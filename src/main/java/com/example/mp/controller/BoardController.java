package com.example.mp.controller;

import com.example.mp.dto.BoardDto;
import com.example.mp.dto.CustomUserDetail;
import com.example.mp.dto.KospiStockDto;
import com.example.mp.entity.BoardEntity;
import com.example.mp.entity.BoardFileEntity;
import com.example.mp.mapper.BoardMapper;
import com.example.mp.service.BoardService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/jpa")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private ResourceLoader resourceLoader;

    private final BoardMapper boardMapper;

    public BoardController(BoardMapper boardMapper){
        this.boardMapper = boardMapper;
    }


    // TODO. 원래 ModelAndView 로 리턴했는데 이러면 JSON 형태로 변환되지 않아 REST 방식에 어긋나 프론트에서 오류가 났음 -> 리스트로 리턴하게 바꿈.
    @GetMapping("/board")
    @Tag(name = "게시판 리스트")
    @Operation(summary = "게시판 리스트 출력", description = "게시판 리스트를 보여줍니다.")
    public List<BoardEntity> openBoardList() {
        return boardService.selectBoardList();
    }


    // 게시판 등록 화면
    @GetMapping("/board/write")
    @Tag(name="게시글 작성")
    @Operation(summary = "게시글 작성", description = "게시글 작성하는 페이지")
    public String openBoardWrite() throws Exception {
        return "/jpaBoardWrite";
    }

    @PostMapping("/board/write")
    @Tag(name = "게시글 생성")
    @Operation(summary = "게시글 생성", description = "게시글을 생성함")
    public ResponseEntity<Void> insertBoard(BoardEntity boardEntity, MultipartHttpServletRequest request) throws Exception {
        boardService.insertBoard(boardEntity, request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/board/{boardIdx}")
    @Tag(name="게시글 상세 페이지")
    @Operation(summary = "게시글 상세 페이지", description = "게시글 상세 페이지를 보여줍니다.")
    @Parameter(name = "boardIdx", description = "게시글 고유 번호", required = true)
    public ResponseEntity<BoardDto> openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
        BoardEntity boardEntity = boardService.selectBoardDetail(boardIdx);
        if (boardEntity == null) {
            return ResponseEntity.notFound().build();
        }

        BoardDto boardDto = boardMapper.toDto(boardEntity); // Entity를 DTO로 변환
        return ResponseEntity.ok(boardDto);
    }

    @GetMapping("/{boardIdx}/{fileId}")
    public ResponseEntity<Resource> getFile(@PathVariable int boardIdx, @PathVariable int fileId) {
        String filePath = "/user/kjh" + boardIdx + "/" + fileId; // 파일 경로 설정
        Resource resource = resourceLoader.getResource("file:" + filePath); // Resource 객체 생성

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 파일이 존재하지 않으면 404 반환
        }
        log.error("@@@@@@!!!!!!!!");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource); // 파일 반환
    }

    // 게시판 수정
    @PutMapping("/board/update/{boardIdx}")
    @Tag(name="게시글 수정")
    @Operation(summary = "게시글 수정", description = "게시글을 수정함")
    public ResponseEntity<Void> updateBoard(
            @PathVariable("boardIdx") int boardIdx,
            @RequestBody BoardDto boardDto) {
        try {
            boardService.updateBoard(boardIdx, boardDto);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            // 예외 처리 및 로그 기록
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

//    @Tag(name="게시글 삭제")
//    @Operation(summary = "게시글 삭제", description = "게시글을 삭제함")
//    @Parameter(name = "boardIdx", description = "삭제할 게시글 고유 번호", required = true)
    @DeleteMapping("/board/{boardIdx}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardIdx") int boardIdx, HttpServletRequest request) {
        boardService.deleteBoard(boardIdx, request);
        return ResponseEntity.noContent().build(); // 성공적으로 삭제되었음을 나타냅니다.
    }
    // 첨부파일 다운로드
    @GetMapping("/board/file/{boardIdx}/{idx}")
    @Tag(name="게시글 파일 다운로드")
    @Operation(summary = "게시글 파일 다운로드", description = "게시글에 있는 파일을 다운로드 받음")
    @Parameter(name = "idx", description = "파일 번호", required = true)
    @Parameter(name = "boardIdx", description = "게시글 고유 번호", required = true)
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


