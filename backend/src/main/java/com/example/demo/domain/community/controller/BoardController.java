package com.example.demo.domain.community.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.community.dto.request.*;
import com.example.demo.domain.community.entity.Board;
import com.example.demo.domain.community.service.BoardService;
import com.example.demo.domain.community.dto.response.*;

import lombok.RequiredArgsConstructor;

import javax.lang.model.type.NullType;
import java.util.*;



@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class BoardController {
    private final BoardService boardService;
//    전체 게시글 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<BoardListResponse>>> getAllBoards(){
    	System.out.println("전체 조회 실행");
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 조회 성공", boardService.getAllBoards(1401L)));
    }
    
//    게시글 하나 조회
    @GetMapping("/{boardSq}")
    public ResponseEntity<ApiResponse<BoardResponse>> getBoard(@PathVariable("boardSq") Long boardSq){
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 조회 성공", boardService.getBoard(boardSq, 1401L)));
    }
    
//    게시글 등록
    @PostMapping
    public ResponseEntity<ApiResponse<NullType>> createBoard(@RequestBody BoardRequest boardRequest){
    	boardService.createBoard(boardRequest);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.CREATED, "게시글 등록이 완료되었습니다.", null));
    }
    
//    게시글 수정
    @PutMapping("/{boardSq}")
    public ResponseEntity<ApiResponse<NullType>> updateBoard(@RequestBody BoardRequest boardRequest, @PathVariable("boardSq") Long boardSq){
    	boardService.updateBoard(boardRequest, boardSq, 82L);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 수정이 완료되었습니다.", null));
    }
    
//    게시글 삭제
    @PatchMapping("/{boardSq}")
    public ResponseEntity<ApiResponse<NullType>> deleteBoard(@PathVariable("boardSq") Long boardSq){
    	System.out.println("게시글 삭제 실행");
    	boardService.deleteBoard(boardSq);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "게시글 삭제가 완료되었습니다.", null));
    }
    
//    게시글 조회수 증가
    @PatchMapping("/{boardSq}/increment-view")
    public ResponseEntity<ApiResponse<NullType>> addViewCntBoard(@PathVariable("boardSq") Long boardSq){
    	System.out.println("조회수 증가 실행");
    	boardService.addViewCntBoard(boardSq);
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "조회수 증가가 완료되었습니다.", null));
    }

}