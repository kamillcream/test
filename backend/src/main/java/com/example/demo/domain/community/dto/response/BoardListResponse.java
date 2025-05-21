package com.example.demo.domain.community.dto.response;

import com.example.demo.domain.community.dto.SkillTagDTO;
import com.example.demo.domain.community.entity.*;

import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardListResponse{
	private Long boardSq;
    private Long userSq;
//    private String userNm; // 사용자 이름
    private String boardTtl;
    private Integer boardViewCnt;
    private Integer boardCommentCnt;
    private Integer boardRecommendCnt;
    private Integer boardAnswerCnt;
    private Long boardAdoptStatusCd;
    private LocalDateTime boardCreatedAtDtm;
    private List<String> normalTags;  // 일반 태그
    private List<SkillTagDTO> skillTags;

    public static BoardListResponse fromEntity(Board board, Integer boardAnswerCnt, List<String> normalTags, List<SkillTagDTO> skillTags) {
        return new BoardListResponse(
			board.getBoardSq(),
			board.getUserSq(),
			board.getBoardTtl(),
			board.getBoardViewCnt(),
			board.getBoardCommentCnt(),
			board.getBoardRecommendCnt(),
			boardAnswerCnt,
			board.getBoardAdoptStatusCd(),
			board.getBoardCreatedAtDtm(),
			normalTags,
			skillTags
        );
    }
	
}