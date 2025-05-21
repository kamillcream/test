package com.example.demo.domain.community.dto.request;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class CommentRequest{
    private Long userSq;
    private Long boardSq;
    private Long answerSq;
    private String description;
}