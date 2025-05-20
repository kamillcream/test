package com.example.demo.domain.community.dto.response;

import java.time.LocalDateTime;

import com.example.demo.domain.community.entity.Board;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse {
    private Long boardSq;
    private Long userSq;
    // private String userNm; // 사용자 이름
    private String boardTtl;
    private String boardDescriptionEdt;
    private Integer boardViewCnt;
    private Integer boardCommentCnt;
    private Integer boardRecommendCnt;
    private Long boardAdoptStatusCd;
    private LocalDateTime boardCreatedAt;
    // private List<Attachment> attachments; // 첨부파일
    // private List<NormalTag> normalTags; // 일반 태그

    public static BoardResponse fromEntity(Board board) {
        return new BoardResponse(
                board.getBoardSq(),
                board.getUserSq(),
                board.getBoardTtl(),
                board.getBoardDescriptionEdt(),
                board.getBoardViewCnt(),
                board.getBoardCommentCnt(),
                board.getBoardRecommendCnt(),
                board.getBoardAdoptStatusCd(),
                board.getBoardCreatedAt());
    }

}