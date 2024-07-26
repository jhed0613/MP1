package com.example.mp.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "comment")
@NoArgsConstructor
@Data
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "board_idx", referencedColumnName = "board_idx", insertable = false, updatable = false)
    private BoardEntity board;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", insertable = false, updatable = false)
    private UserEntity user;

    private String content;

    @Column(name = "created_at")
    private LocalDateTime createTime = LocalDateTime.now();
}
