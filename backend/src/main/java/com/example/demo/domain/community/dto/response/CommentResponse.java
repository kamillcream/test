package com.example.demo.domain.community.dto.response;

import com.example.demo.domain.community.entity.*;
import com.example.demo.domain.user.dto.*;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {
    private Long sq;
    private Long userSq;
    private String userProfileImgUrl;
    private String userNm;
    private String description;
    private LocalDateTime createdAt;
    private Integer recommendCnt;

    public static CommentResponse fromEntity(Comment comment, UserDTO userDto, String profileImageUrl) {
        String userNm = "존재하지 않는 사용자";

        if (userDto != null) {
            if (userDto.getUserNm() != null) {
                userNm = userDto.getUserNm();
            }
        }

        return new CommentResponse(
                comment.getCommentSq(),
                comment.getUserSq(),
                profileImageUrl,
                userNm,
                comment.getCommentDescriptionTxt(),
                comment.getCommentCreatedAtDtm(),
                comment.getCommentRecommendCnt());
    }
}