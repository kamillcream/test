package com.example.demo.domain.community.service;


import org.springframework.stereotype.Service;

import com.example.demo.domain.community.dto.request.*;
import com.example.demo.domain.community.entity.Board;
import com.example.demo.domain.community.mapper.BoardMapper;
import com.example.demo.domain.community.dto.response.*;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    @Transactional
    public List<BoardListResponse> getAllBoards(Long boardTypeCd) {
    	List<Board> boards = boardMapper.findAll(boardTypeCd);
    	System.out.println(boards);
    	if(boards == null) {
    		throw new IllegalArgumentException("게시글이 없습니다.");
    	}
        return boards.stream().filter(Objects::nonNull).map(BoardListResponse::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public BoardResponse getBoard(Long boardSq, Long boardTypeCd) {
    	Board board = boardMapper.findByIdBoard(boardSq, boardTypeCd);
    	if(board == null) {
    		throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
    	} else if(board.getBoardIsDeletedYn() == "Y") {
    		throw new IllegalArgumentException("삭제된 게시글입니다.");
    	}
    	System.out.println(boardSq);
        return BoardResponse.fromEntity(board);
    }
    
    @Transactional
    public void createBoard(BoardRequest boardRequest){
    	if(boardRequest.getBoardTtl() == null) {
    		throw new IllegalArgumentException("제목을 입력해주세요.");
    	} else if(boardRequest.getBoardDescriptionEdt() == null) {
    		throw new IllegalArgumentException("내용을 입력해주세요.");
    	}
        boardMapper.insert(Board.builder()
        		.userSq(boardRequest.getUserSq())
        		.boardTtl(boardRequest.getBoardTtl())
        		.boardDescriptionEdt(boardRequest.getBoardDescriptionEdt())
        		.boardTyp("normal")
        		.boardTypeCd(1401L).build());
		return;
    }

    @Transactional
    public void updateBoard(BoardRequest boardRequest, Long boardSq, Long boardTypeCd) {
    	if(boardRequest.getBoardTtl() == null) {
    		throw new IllegalArgumentException("제목을 입력해주세요.");
    	} else if(boardRequest.getBoardDescriptionEdt() == null) {
    		throw new IllegalArgumentException("내용을 입력해주세요.");
    	}
    	
        Board board = boardMapper.findByIdBoard(boardSq, boardTypeCd);

        board.setBoardTtl(boardRequest.getBoardTtl());
        board.setBoardDescriptionEdt(boardRequest.getBoardDescriptionEdt());
        
        boardMapper.update(board);

        return;
    }

    @Transactional
    public void deleteBoard(Long boardSq) {
        boardMapper.delete(boardSq);
    }

    @Transactional
    public void addViewCntBoard(Long boardSq) {
        boardMapper.addViewCnt(boardSq);
    }


}
