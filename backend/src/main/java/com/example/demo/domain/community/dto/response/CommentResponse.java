package com.example.demo.domain.community.dto.response;

import com.example.demo.domain.community.entity.*;
import com.example.demo.domain.user.dto.*;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse{
	private Long commentSq;
    private Long userSq;
    private String userProfileImgUrl;
    private String userNm;
    private String commentDescriptionTxt;
    private LocalDateTime commentCreatedAtDtm;
    private Integer commentRecommendCnt;

//    추후 userMapper 연결 후 사용
//    public static CommentResponse fromEntity(Comment comment, UserDTO userDto) {
//        return new CommentResponse(
//			comment.getCommentSq(),
//			comment.getUserSq(),
//			userDto.getUserProfileImageUrl(),
//			userDto.getUserNm(),
//			comment.getCommentDescriptionTxt(),
//			comment.getCommentCreatedAtDtm(),
//			comment.getCommentRecommendCnt()
//        );
//    }
    
//  임시 사용
	  public static CommentResponse fromEntity(Comment comment) {
	      return new CommentResponse(
				comment.getCommentSq(),
				comment.getUserSq(),
				"profileImgUrl.jpg",
				"홍길동",
				comment.getCommentDescriptionTxt(),
				comment.getCommentCreatedAtDtm(),
				comment.getCommentRecommendCnt()
	      );
	  }
	
}