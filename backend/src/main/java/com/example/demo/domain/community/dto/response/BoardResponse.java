package com.example.demo.domain.community.dto.response;

import com.example.demo.domain.community.dto.SkillTagDTO;
import com.example.demo.domain.community.entity.Board;

import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardResponse{
	private Long boardSq;
    private Long userSq;
//    private String userNm; // 사용자 이름
    private String boardTtl;
    private String boardDescriptionEdt;
    private Integer boardViewCnt;
    private Integer boardCommentCnt;
    private Integer boardRecommendCnt;
    private Long boardAdoptStatusCd;
    private LocalDateTime boardCreatedAt;
//    private List<Attachment> attachments; // 첨부파일
    private List<String> normalTags;  // 일반 태그
    private List<SkillTagDTO> skillTags;
    private List<AnswerListResponse> answers;
    private List<CommentResponse> comments;

    
    public static BoardResponse fromEntity(Board board, List<String> normalTags, List<SkillTagDTO> skillTags, List<AnswerListResponse> answers, List<CommentResponse> comments) {
        return new BoardResponse(
			board.getBoardSq(),
			board.getUserSq(),
			board.getBoardTtl(),
			board.getBoardDescriptionEdt(),
			board.getBoardViewCnt(),
			board.getBoardCommentCnt(),
			board.getBoardRecommendCnt(),
			board.getBoardAdoptStatusCd(),
			board.getBoardCreatedAtDtm(),
			normalTags,
			skillTags,
			answers,
			comments
        );
    }
	
}