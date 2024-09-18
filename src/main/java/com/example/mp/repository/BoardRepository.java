package com.example.mp.repository;

import com.example.mp.entity.BoardEntity;
import com.example.mp.entity.BoardFileEntity;
import com.example.mp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Integer> {
    List<BoardEntity> findAllByOrderByBoardIdxDesc();
    @Query("SELECT file FROM BoardFileEntity file WHERE file.id = :id AND file.board.boardIdx = :boardIdx")
    public BoardFileEntity findBoardFile(@Param("id") int idx, @Param("boardIdx") int boardIdx);

    Optional<BoardEntity> findByBoardIdxAndCreatedUsername(int boardIdx, String username);
}
