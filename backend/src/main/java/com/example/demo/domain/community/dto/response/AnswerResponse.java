package com.example.demo.domain.community.dto.response;

import com.example.demo.domain.community.dto.*;
import com.example.demo.domain.community.entity.*;

import lombok.*;
import java.time.LocalDateTime;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerResponse{
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
    private LocalDateTime answerCreatedAtDtm;
//    private List<Attachment> attachments; // 첨부파일
    private List<String> normalTags;
    private List<SkillTagDTO> skillTags;
    private List<CommentResponse> comments;

    
    public static AnswerResponse fromEntity(Answer answer, List<String> normalTags, List<SkillTagDTO> skillTags, List<CommentResponse> comments) {
        return new AnswerResponse(
    		answer.getAnswerSq(),
    		answer.getUserSq(),
    		answer.getBoardSq(),
    		answer.getAnswerTtl(),
    		answer.getAnswerDescriptionEdt(),
    		answer.getAnswerViewCnt(),
    		answer.getAnswerCommentCnt(),
    		answer.getAnswerRecommendCnt(),
    		answer.getAnswerIsAdoptedYn(),
    		answer.getAnswerCreatedAtDtm(),
			normalTags,
			skillTags,
			comments
        );
    }
	
}