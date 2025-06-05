package com.example.demo.domain.community.controller;


import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.community.dto.request.*;
import com.example.demo.domain.community.service.BoardService;
import com.example.demo.domain.community.dto.response.*;

import lombok.RequiredArgsConstructor;

import javax.lang.model.type.NullType;
import java.util.*;



@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {
    private final BoardService boardService;
//    전체 QnA 조회
    @GetMapping
    public ResponseEntity<ApiResponse<BoardListResponse>> getAllQnas(
    		@RequestParam(value = "boardAdoptStatusCd", required = false) Long boardAdoptStatusCd, 
    		@RequestParam(value = "searchType", required = false) String searchType, 
    		@RequestParam(value = "keyword", required = false) String keyword,
    		@RequestParam(value = "skillTags", required = false) List<Long> skillTags,
    		@RequestParam(value = "sortType", defaultValue = "latest") String sortType,
    		@RequestParam(value = "page", defaultValue = "1") Long page,
    		@RequestParam(value = "size", defaultValue = "10") Long size){
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 조회 성공", boardService.getAllBoards(1402L, boardAdoptStatusCd, searchType, keyword, skillTags, sortType, page, size)));
    }
    
//    QnA 하나 조회
    @GetMapping("/{boardSq}")
    public ResponseEntity<ApiResponse<BoardResponse>> getQna(@AuthenticationPrincipal Long userSq, @PathVariable("boardSq") Long boardSq){
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 조회 성공", boardService.getBoard(userSq, boardSq, 1402L)));
    }
    
//    QnA 등록
    @PostMapping
    public ResponseEntity<ApiResponse<NullType>> createQna(@AuthenticationPrincipal Long userSq, @RequestBody BoardRequest boardRequest){
    	boardRequest.setUserSq(userSq);
    	boardService.createBoard(boardRequest, 1402L);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.CREATED, "게시글 등록이 완료되었습니다.", null));
    }
    
//    QnA 수정
    @PutMapping("/{boardSq}")
    public ResponseEntity<ApiResponse<NullType>> updateQna(@AuthenticationPrincipal Long userSq, @RequestBody BoardRequest boardRequest, @PathVariable("boardSq") Long boardSq){
    	boardRequest.setUserSq(userSq);
    	boardService.updateBoard(boardRequest, boardSq, 1402L);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 수정이 완료되었습니다.", null));
    }
    
//    QnA 삭제
    @PatchMapping("/{boardSq}")
    public ResponseEntity<ApiResponse<NullType>> deleteQna(@AuthenticationPrincipal Long userSq, @PathVariable("boardSq") Long boardSq){
    	boardService.deleteBoard(userSq, boardSq);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 삭제가 완료되었습니다.", null));
    }
    
//    QnA 조회수 증가
    @PatchMapping("/{boardSq}/increment-view")
    public ResponseEntity<ApiResponse<NullType>> addViewCntQna(@PathVariable("boardSq") Long boardSq){
    	boardService.addViewCntBoard(boardSq);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "조회수 증가가 완료되었습니다.", null));
    }
    
//  QnA 추천 클릭
    @PostMapping("/{boardSq}/recommend")
    public ResponseEntity<ApiResponse<NullType>> updateRecommendQna(@AuthenticationPrincipal Long userSq, @PathVariable("boardSq") Long boardSq){
		  boardService.updateBoardRecommend(userSq, boardSq);
		  return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "추천 반영이 완료되었습니다.", null));
    }
	  

}