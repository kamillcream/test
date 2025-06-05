package com.example.demo.domain.community.service;


import org.springframework.stereotype.Service;

import com.example.demo.domain.community.dto.*;
import com.example.demo.domain.community.dto.request.*;
import com.example.demo.domain.community.entity.*;
import com.example.demo.domain.community.mapper.*;
import com.example.demo.domain.community.dto.response.*;
import com.example.demo.domain.community.converter.*;
import com.example.demo.domain.user.dto.UserDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardMapper boardMapper;
    private final CmntTagMapper cmntTagMapper;
    private final CommentMapper commentMapper;
    private final AnswerMapper answerMapper;
    private final NormalTagConverter normalTagConverter;
    private final SkillTagConverter skillTagConverter;
    private final RecommendationMapper recommendationMapper;
    private final CommunityUserMapper communityUserMapper;
    private final AnswerService answerService;

    @Transactional
    public BoardListResponse getAllBoards(Long boardTypeCd, Long boardAdoptStatusCd, String searchType, String keyword, List<Long> searchSkillTags, String sortType, Long page, Long size) {
    	if(page < 1) page = 1L;
    	Long offset = (page - 1L) * size;
    	if(sortType == null || sortType.isEmpty()) sortType = "latest";
    	
    	
    	List<Board> boards = boardMapper.findAll(boardTypeCd, boardAdoptStatusCd, searchType, keyword, searchSkillTags, sortType, size, offset);
    	Long totalElements = boardMapper.findAllCnt(boardTypeCd, boardAdoptStatusCd, searchType, keyword, searchSkillTags, sortType, size, offset);
    	
    	List<BoardListDTO> responses = boards.stream()
            .filter(Objects::nonNull)
            .map(board -> {
                // 각 게시글의 일반 태그 조회
                List<String> normalTags = normalTagConverter.convertNormalTagsToStrings(cmntTagMapper.findNT(board.getBoardSq(), null));
                
                // 각 게시글의 스킬 태그 조회
            	List<SkillTagDTO> skillTags = skillTagConverter.convertSkillTagsToStrings(cmntTagMapper.findST(board.getBoardSq(), null));
            	
            	// 각 게시글의 답변 리스트 조회
            	Integer boardAnswerCnt = answerMapper.findAllCnt(board.getBoardSq());
            	
            	// 각 게시글의 작성자 조회
            	UserDTO userInfo = communityUserMapper.findById(board.getUserSq());
            	String userNm = Optional.ofNullable(userInfo)
            	                        .map(UserDTO::getUserNm)
            	                        .orElse("존재하지 않는 사용자");
                
                // BoardListResponse 생성 (태그 포함)
                return BoardListDTO.fromEntity(board, userNm, boardAnswerCnt, normalTags, skillTags);
            })
            .collect(Collectors.toList());

        return BoardListResponse.builder().page(page).size(size).totalElements(totalElements).boards(responses).build();
    }

    @Transactional
    public BoardResponse getBoard(Long userSq, Long boardSq, Long boardTypeCd) {
    	Board board = boardMapper.findByIdBoard(boardSq, boardTypeCd);
    	if(board == null) {
    		throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
    	} else if(board.getBoardIsDeletedYn().equals("Y")) {
    		throw new IllegalArgumentException("삭제된 게시글입니다.");
    	}

    	List<String> normalTags = normalTagConverter.convertNormalTagsToStrings(cmntTagMapper.findNT(boardSq, null));
    	List<SkillTagDTO> skillTags = skillTagConverter.convertSkillTagsToStrings(cmntTagMapper.findST(boardSq, null));
    	
    	// 각 게시글의 작성자 조회
    	UserDTO userInfo = communityUserMapper.findById(board.getUserSq());
    	String userNm = Optional.ofNullable(userInfo)
    			.map(UserDTO::getUserNm)
                    .orElse("존재하지 않는 사용자");
    	
    	List<AnswerListResponse> answerListResponses = answerService.getAllAnswers(board.getBoardSq());
    	
    	// 각 게시글의 댓글 조회
    	List<CommentResponse> comments = commentMapper.findByBoardSq(boardSq).stream()
    		    .filter(Objects::nonNull)
    		    .map(comment -> {
    		        UserDTO userDto = communityUserMapper.findById(comment.getUserSq());
    		        return CommentResponse.fromEntity(comment, userDto);
    		    })
    		    .collect(Collectors.toList());
    			
    	
        return BoardResponse.fromEntity(board, userNm, normalTags, skillTags, answerListResponses, comments, userSq);
    }
    
    @Transactional
    public void createBoard(BoardRequest boardRequest, Long BoardTypeCd){
//    	게시글 오류 처리
    	if(boardRequest.getTtl() == null) {
    		throw new IllegalArgumentException("제목을 입력해주세요.");
    	} else if(boardRequest.getDescription() == null) {
    		throw new IllegalArgumentException("내용을 입력해주세요.");
    	}
    	
    	Board board = Board.builder()
    			.userSq(boardRequest.getUserSq())
        		.boardTtl(boardRequest.getTtl())
        		.boardDescriptionEdt(boardRequest.getDescription())
        		.boardTyp(BoardTypeCd == 1401L ? "normal" : "qna")
        		.boardTypeCd(BoardTypeCd).build();
        boardMapper.insert(board);
        
        if (board.getBoardSq() == null) {
            throw new IllegalStateException("게시글 생성 실패: Primary Key가 생성되지 않았습니다.");
        }
        
//        일반 태그 추가
        if(boardRequest.getNormalTags().size() > 0) {
        	cmntTagMapper.insertNT(normalTagConverter.convertStringsToNormalTags(board.getBoardSq(), null, boardRequest.getNormalTags()));
        }
    	
//    	스킬태그 추가
    	if(board.getBoardTypeCd() == 1402 && boardRequest.getSkillTags().size() > 0) {
    		cmntTagMapper.insertST(skillTagConverter.convertStringsToSkillTags(board.getBoardSq(), null, boardRequest.getSkillTags()));
    	}
        
        
		return;
    }

    @Transactional
    public void updateBoard(BoardRequest boardRequest, Long boardSq, Long boardTypeCd) {
//    	게시글 업데이트
    	if(boardRequest.getTtl() == null) {
    		throw new IllegalArgumentException("제목을 입력해주세요.");
    	} else if(boardRequest.getDescription() == null) {
    		throw new IllegalArgumentException("내용을 입력해주세요.");
    	}
    	
        Board board = boardMapper.findByIdBoard(boardSq, boardTypeCd);

        
        if(board.getUserSq() != boardRequest.getUserSq()) {
        	throw new IllegalArgumentException("작성자와 사용자가 일치하지 않습니다.");
        }

        board.setBoardTtl(boardRequest.getTtl());
        board.setBoardDescriptionEdt(boardRequest.getDescription());
        
        if(boardRequest.getBoardAdoptStatusCd() != null) {
        	board.setBoardAdoptStatusCd(boardRequest.getBoardAdoptStatusCd());
        }
        
        boardMapper.update(board);
        
//      기존 태그 삭제
        cmntTagMapper.deleteNT(board.getBoardSq(), null);
        cmntTagMapper.deleteST(board.getBoardSq(), null);
        
//      일반 태그 추가
	  	if(boardRequest.getNormalTags().size() > 0) {
	  		cmntTagMapper.insertNT(normalTagConverter.convertStringsToNormalTags(board.getBoardSq(), null, boardRequest.getNormalTags()));
		}
  	
//  	스킬태그 추가
	  	if(board.getBoardTypeCd() == 1402 && boardRequest.getSkillTags().size() > 0) {
	  		cmntTagMapper.insertST(skillTagConverter.convertStringsToSkillTags(board.getBoardSq(), null, boardRequest.getSkillTags()));
	  	}
	        

        return;
    }

    @Transactional
    public void deleteBoard(Long userSq, Long boardSq) {
        boardMapper.delete(userSq, boardSq);
    	cmntTagMapper.deleteNT(boardSq, null);
    	cmntTagMapper.deleteST(boardSq, null);
    	recommendationMapper.deleteAll(boardSq, null, null);

    }

    @Transactional
    public void addViewCntBoard(Long boardSq) {
        boardMapper.addViewCnt(boardSq);
    }

//    추천
    @Transactional
    public void updateBoardRecommend(Long userSq, Long boardSq) {
    	
    	if(userSq == null) {
    		throw new IllegalArgumentException("로그인 후 이용해주세요.");
    	}
        
        Recommendation recommendation = recommendationMapper.findByBoardSq(userSq, boardSq);
        
        if(recommendation == null) {
        	recommendation = Recommendation.builder().boardSq(boardSq).userSq(userSq).recommendationTypeCd(1901L).build();
        	recommendationMapper.insert(recommendation);
        	
        } else {
        	recommendationMapper.delete(recommendation.getRecommendationSq());
        }
        
        boardMapper.updateRecommendCnt(boardSq);
        
        return;
        
        
    }
    
//    전체 스킬 태그 리스트 조회
    @Transactional
    public List<CommonSkillTag> getAllSkillTags() {
    	List<CommonSkillTag> parentTags = cmntTagMapper.findParentSkillTags();
    	List<CommonSkillTag> childrenTags = cmntTagMapper.findAll(parentTags);
    	List<CommonSkillTag> allTags = new ArrayList<>();
    	allTags.addAll(parentTags);
    	allTags.addAll(childrenTags);
    	
    	return allTags;
    	
    }


}
