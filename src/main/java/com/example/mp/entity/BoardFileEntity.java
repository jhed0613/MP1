package com.example.mp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "file")
@NoArgsConstructor
@Data
public class BoardFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "board_idx", referencedColumnName = "board_idx")
    private BoardEntity board;

    @Column(name = "filename")
    private String fileName;

    @Column(name = "filepath")
    private String filePath;

    @Column(name = "filesize")
    private String fileSize;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createTime = LocalDateTime.now();
}
