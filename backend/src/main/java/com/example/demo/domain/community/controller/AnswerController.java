package com.example.demo.domain.community.controller;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.community.dto.request.*;
import com.example.demo.domain.community.service.*;
import com.example.demo.domain.community.dto.response.*;

import lombok.RequiredArgsConstructor;

import javax.lang.model.type.NullType;



@RestController
@RequestMapping("/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;
    
//    답변 하나 조회
    @GetMapping("/{answerSq}")
    public ResponseEntity<ApiResponse<AnswerResponse>> getAnswer(@PathVariable("answerSq") Long answerSq){
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "답변 조회 성공", answerService.getAnswer(answerSq)));
    }
    
//    답변 등록
    @PostMapping
    public ResponseEntity<ApiResponse<NullType>> createAnswer(@AuthenticationPrincipal Long userSq, @RequestBody AnswerRequest answerRequest){
    	System.out.println("답변 등록");
    	answerRequest.setUserSq(userSq);
    	answerService.createAnswer(answerRequest);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.CREATED, "답변 등록이 완료되었습니다.", null));
    }
    
//    답변 수정
    @PutMapping("/{answerSq}")
    public ResponseEntity<ApiResponse<NullType>> updateAnswer(@AuthenticationPrincipal Long userSq, @RequestBody AnswerRequest answerRequest, @PathVariable("answerSq") Long answerSq){
    	answerRequest.setUserSq(userSq);
    	answerService.updateAnswer(answerRequest, answerSq);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "답변 수정이 완료되었습니다.", null));
    }
    
//    답변 삭제
    @PatchMapping("/{answerSq}")
    public ResponseEntity<ApiResponse<NullType>> deleteAnswer(@AuthenticationPrincipal Long userSq, @PathVariable("answerSq") Long answerSq){
    	answerService.deleteAnswer(userSq, answerSq);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "답변 삭제가 완료되었습니다.", null));
    }
    
//    답변 조회수 증가
    @PatchMapping("/{answerSq}/increment-view")
    public ResponseEntity<ApiResponse<NullType>> addViewCntAnswer(@PathVariable("answerSq") Long answerSq){
    	answerService.addViewCntAnswer(answerSq);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "조회수 증가가 완료되었습니다.", null));
    }
    
//  답변 추천 클릭
	@PostMapping("/{answerSq}/recommend")
	public ResponseEntity<ApiResponse<NullType>> updateRecommendAnswer(@AuthenticationPrincipal Long userSq, @PathVariable("answerSq") Long answerSq){
		answerService.updateAnswerRecommend(userSq, answerSq);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "추천 반영이 완료되었습니다.", null));
	}

}