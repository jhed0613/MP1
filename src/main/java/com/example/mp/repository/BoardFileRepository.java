package com.example.mp.repository;

import com.example.mp.entity.BoardEntity;
import com.example.mp.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Integer> {
    List<BoardFileEntity> findByBoard_BoardIdx(int boardIdx);
    List<BoardFileEntity> findByBoard(BoardEntity board);
}
