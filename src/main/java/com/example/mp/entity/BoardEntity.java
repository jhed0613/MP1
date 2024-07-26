package com.example.mp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@NoArgsConstructor
@Table(name = "Board")
@Data
public class BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "board_idx")
    private Integer boardIdx;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "hit_count")
    private int hitCnt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createTime = LocalDateTime.now();

    @Column(name = "updated_at")
    private LocalDateTime updateTime;

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Collection<BoardFileEntity> fileInfoList;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;
}

