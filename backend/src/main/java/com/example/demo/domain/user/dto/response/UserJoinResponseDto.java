package com.example.demo.domain.user.dto.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserJoinResponseDto {
    private Long userSq;
    private String userEmail;
    private String userNm;
    private LocalDateTime userCreatedAtDtm;
}
