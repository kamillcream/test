package com.example.demo.domain.community.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardSq;
    private Long userSq;
    private String boardTtl;
    private String boardDescriptionEdt;
    private Integer boardViewCnt = 0;
    private Integer boardCommentCnt = 0;
    private Integer boardRecommendCnt = 0;
    private Long boardAdoptStatusCd = 1501L;
    private String boardIsDeletedYn = "N";
    private String boardTyp;
    private Long boardTypeCd;
    private LocalDateTime boardCreatedAt = LocalDateTime.now();

    // public Board(Long userSq, String boardTtl, String boardDescriptionEdt, String
    // boardTyp, Long boardTypeCd){
    // this.userSq = userSq;
    // this.boardTtl = boardTtl;
    // this.boardDescriptionEdt = boardDescriptionEdt;
    // this.boardTyp = boardTyp;
    // this.boardTypeCd = boardTypeCd;
    // this.boardViewCnt = 0;
    // this.boardCommentCnt = 0;
    // this.boardRecommendCnt = 0;
    // this.boardAdoptStatusCd = 84L;
    // this.boardIsDeletedYn = "N";
    // this.boardCreatedAt = LocalDateTime.now();
    // }
}
