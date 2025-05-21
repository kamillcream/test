package com.example.demo.domain.user.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserJoinResponseDTO {
    private Long userSq;
    private String userEmail;
    private String userNm;
    private LocalDateTime userCreatedAtDtm;
}
