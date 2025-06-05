package com.example.demo.domain.community.service;


import org.springframework.stereotype.Service;

import com.example.demo.domain.community.dto.*;
import com.example.demo.domain.community.dto.request.*;
import com.example.demo.domain.community.entity.*;
import com.example.demo.domain.community.mapper.*;
import com.example.demo.domain.user.dto.UserDTO;
import com.example.demo.domain.community.dto.response.*;
import com.example.demo.domain.community.converter.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class AnswerService {

    private final AnswerMapper answerMapper;
    private final CmntTagMapper cmntTagMapper;
    private final CommentMapper commentMapper;
    private final NormalTagConverter normalTagConverter;
    private final SkillTagConverter skillTagConverter;
    private final RecommendationMapper recommendationMapper;
    private final CommunityUserMapper communityUserMapper;

//    답변 리스트
    @Transactional
    public List<AnswerListResponse> getAllAnswers(Long boardSq) {
    	List<Answer> answers = answerMapper.findAll(boardSq);
    	
    	List<AnswerListResponse> responses = answers.stream()
            .filter(Objects::nonNull)
            .map(answer -> {
            	if(answer.getAnswerIsDeletedYn().equals("Y")) {
            		return AnswerListResponse.builder().isDeletedYn("Y").build(); 
            	} else {
            		UserDTO userInfo = communityUserMapper.findById(answer.getUserSq());
                    String userNm = "존재하지 않는 사용자";
                    if (userInfo != null && userInfo.getUserNm() != null) {
                        userNm = userInfo.getUserNm();
                    }

                    return AnswerListResponse.fromEntity(answer, userNm);            		            		
            	}
            })
            .collect(Collectors.toList());

        return responses;
    }

//    답변 하나 조회
    @Transactional
    public AnswerResponse getAnswer(Long answerSq) {
    	Answer answer = answerMapper.findById(answerSq);
    	if(answer == null) {
    		throw new IllegalArgumentException("답변이 존재하지 않습니다.");
    	} else if(answer.getAnswerIsDeletedYn().equals("Y")) {
    		throw new IllegalArgumentException("삭제된 답변입니다.");
    	}

    	List<String> normalTags = normalTagConverter.convertNormalTagsToStrings(cmntTagMapper.findNT(null, answerSq));
    	List<SkillTagDTO> skillTags = skillTagConverter.convertSkillTagsToStrings(cmntTagMapper.findST(null, answerSq));
    	

    	UserDTO userInfo = communityUserMapper.findById(answer.getUserSq());
    	String userNm = Optional.ofNullable(userInfo)
    			.map(UserDTO::getUserNm)
                    .orElse("존재하지 않는 사용자");

    	List<CommentResponse> comments = commentMapper.findByAnswerSq(answerSq).stream()
    		    .filter(Objects::nonNull)
    		    .map(comment -> {
    		        UserDTO userDto = communityUserMapper.findById(comment.getUserSq());
    		        return CommentResponse.fromEntity(comment, userDto);
    		    })
    		    .collect(Collectors.toList());
    			
    	
        return AnswerResponse.fromEntity(answer, userNm, normalTags, skillTags, comments);
    }
    
//    답변 등록
    @Transactional
    public void createAnswer(AnswerRequest answerRequest){
//    	게시글 오류 처리
    	if(answerRequest.getTtl() == null) {
    		throw new IllegalArgumentException("제목을 입력해주세요.");
    	} else if(answerRequest.getDescription() == null) {
    		throw new IllegalArgumentException("내용을 입력해주세요.");
    	}
    	
    	Answer answer = Answer.builder()
    			.userSq(answerRequest.getUserSq())
    			.boardSq(answerRequest.getBoardSq())
        		.answerTtl(answerRequest.getTtl())
        		.answerDescriptionEdt(answerRequest.getDescription()).build();
        answerMapper.insert(answer);
        
        if (answer.getAnswerSq() == null) {
            throw new IllegalStateException("게시글 등록 실패하였습니다.");
        }
        
//        일반 태그 추가
        if(answerRequest.getNormalTags().size() > 0) {
        	cmntTagMapper.insertNT(normalTagConverter.convertStringsToNormalTags(null, answer.getAnswerSq(), answerRequest.getNormalTags()));        	
        }
    	
//    	스킬태그 추가
        if(answerRequest.getSkillTags().size() > 0) {
        	cmntTagMapper.insertST(skillTagConverter.convertStringsToSkillTags(null, answer.getAnswerSq(), answerRequest.getSkillTags()));        	
        }
        
        
		return;
    }

//    답변 수정
    @Transactional
    public void updateAnswer(AnswerRequest answerRequest, Long answerSq) {
//    	게시글 업데이트
    	if(answerRequest.getTtl() == null) {
    		throw new IllegalArgumentException("제목을 입력해주세요.");
    	} else if(answerRequest.getDescription() == null) {
    		throw new IllegalArgumentException("내용을 입력해주세요.");
    	}
    	
        Answer answer = answerMapper.findById(answerSq);
        
        if(answer.getUserSq() != answerRequest.getUserSq()) {
        	throw new IllegalArgumentException("작성자와 사용자가 일치하지 않습니다.");
        }

        answer.setAnswerTtl(answerRequest.getTtl());
        answer.setAnswerDescriptionEdt(answerRequest.getDescription());
        
        answerMapper.update(answer);
        
//      기존 태그 삭제
        cmntTagMapper.deleteNT(null, answer.getAnswerSq());
        cmntTagMapper.deleteST(null, answer.getAnswerSq());
        
//      일반 태그 추가
      if(answerRequest.getNormalTags().size() > 0) {
      	cmntTagMapper.insertNT(normalTagConverter.convertStringsToNormalTags(null, answer.getAnswerSq(), answerRequest.getNormalTags()));        	
      }
//  	스킬태그 추가
      if(answerRequest.getSkillTags().size() > 0) {
      	cmntTagMapper.insertST(skillTagConverter.convertStringsToSkillTags(null, answer.getAnswerSq(), answerRequest.getSkillTags()));        	
      }
        

        return;
    }

//    답변 삭제
    @Transactional
    public void deleteAnswer(Long userSq, Long answerSq) {
    	answerMapper.delete(userSq, answerSq);
    	cmntTagMapper.deleteNT(null, answerSq);
    	cmntTagMapper.deleteST(null, answerSq);
    	recommendationMapper.deleteAll(null, answerSq, null);

    }

//    조회수 증가
    @Transactional
    public void addViewCntAnswer(Long answerSq) {
    	answerMapper.addViewCnt(answerSq);
    }

//    추천
    @Transactional
    public void updateAnswerRecommend(Long userSq, Long answerSq) {
        
        Recommendation recommendation = recommendationMapper.findByAnswerSq(userSq, answerSq);
        
        if(recommendation == null) {
        	recommendation = Recommendation.builder().answerSq(answerSq).userSq(userSq).recommendationTypeCd(1902L).build();
        	recommendationMapper.insert(recommendation);
        	
        } else {
        	recommendationMapper.delete(recommendation.getRecommendationSq());
        }
        
        answerMapper.updateRecommendCnt(answerSq);
        
        return;
        
        
    }


}
