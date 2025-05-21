package com.example.demo.domain.community.dto.response;

import com.example.demo.domain.community.entity.*;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnswerListResponse{
	private Long answerSq;
    private Long userSq;
    private Long boardSq;
//    private String userNm; // 사용자 이름
    private String answerTtl;
    private String answerDescriptionEdt;
    private Integer answerViewCnt;
    private Integer answerCommentCnt;
    private Integer answerRecommendCnt;
    private String answerIsAdoptedYn;
    private String answerIsDeletedYn;
    private LocalDateTime answerCreatedAtDtm;

    
    public static AnswerListResponse fromEntity(Answer answer) {
        return new AnswerListResponse(
    		answer.getAnswerSq(),
    		answer.getUserSq(),
    		answer.getBoardSq(),
    		answer.getAnswerTtl(),
    		answer.getAnswerDescriptionEdt(),
    		answer.getAnswerViewCnt(),
    		answer.getAnswerCommentCnt(),
    		answer.getAnswerRecommendCnt(),
    		answer.getAnswerIsAdoptedYn(),
    		answer.getAnswerIsDeletedYn(),
    		answer.getAnswerCreatedAtDtm()
        );
    }
	
}