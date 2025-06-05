package com.example.demo.domain.community.controller;


import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.community.dto.request.*;
import com.example.demo.domain.community.service.BoardService;
import com.example.demo.domain.community.dto.response.*;
import com.example.demo.domain.community.entity.*;

import lombok.RequiredArgsConstructor;

import javax.lang.model.type.NullType;

import java.util.*;



@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
//    전체 게시글 조회
    @GetMapping
    public ResponseEntity<ApiResponse<BoardListResponse>> getAllBoards(
    		@RequestParam(value = "searchType", required = false) String searchType, 
    		@RequestParam(value = "keyword", required = false) String keyword,
    		@RequestParam(value = "sortType", defaultValue = "latest") String sortType,
    		@RequestParam(value = "page", defaultValue = "1") Long page,
    		@RequestParam(value = "size", defaultValue = "10") Long size){
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 조회 성공", boardService.getAllBoards(1401L, null, searchType, keyword, null, sortType, page, size)));
    }
    
//    게시글 하나 조회
    @GetMapping("/{boardSq}")
    public ResponseEntity<ApiResponse<BoardResponse>> getBoard(@AuthenticationPrincipal Long userSq, @PathVariable("boardSq") Long boardSq){
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 조회 성공", boardService.getBoard(userSq, boardSq, 1401L)));
    }
    
//    게시글 등록
    @PostMapping
    public ResponseEntity<ApiResponse<NullType>> createBoard(@AuthenticationPrincipal Long userSq, @RequestBody BoardRequest boardRequest){
    	boardRequest.setUserSq(userSq);
    	boardService.createBoard(boardRequest, 1401L);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.CREATED, "게시글 등록이 완료되었습니다.", null));
    }
    
//    게시글 수정
    @PutMapping("/{boardSq}")
    public ResponseEntity<ApiResponse<NullType>> updateBoard(@AuthenticationPrincipal Long userSq, @RequestBody BoardRequest boardRequest, @PathVariable("boardSq") Long boardSq){
    	boardRequest.setUserSq(userSq);
    	boardService.updateBoard(boardRequest, boardSq, 1401L);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 수정이 완료되었습니다.", null));
    }
    
//    게시글 삭제
    @PatchMapping("/{boardSq}")
    public ResponseEntity<ApiResponse<NullType>> deleteBoard(@AuthenticationPrincipal Long userSq, @PathVariable("boardSq") Long boardSq){
    	boardService.deleteBoard(userSq, boardSq);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 삭제가 완료되었습니다.", null));
    }
    
//    게시글 조회수 증가
    @PatchMapping("/{boardSq}/increment-view")
    public ResponseEntity<ApiResponse<NullType>> addViewCntBoard(@PathVariable("boardSq") Long boardSq){
    	boardService.addViewCntBoard(boardSq);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "조회수 증가가 완료되었습니다.", null));
    }
    
//  게시글 추천 클릭
	@PostMapping("/{boardSq}/recommend")
	public ResponseEntity<ApiResponse<NullType>> updateRecommendBoard(@AuthenticationPrincipal Long userSq, @PathVariable("boardSq") Long boardSq){
		boardService.updateBoardRecommend(userSq, boardSq);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "추천 반영이 완료되었습니다.", null));
	}
	
//	전체 스킬 태그 리스트 조회
	@GetMapping("/skill-tags")
	public ResponseEntity<ApiResponse<List<CommonSkillTag>>> getAllSkills() {
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "기술 태그 리스트 조회 완료", boardService.getAllSkillTags()));
	}

}