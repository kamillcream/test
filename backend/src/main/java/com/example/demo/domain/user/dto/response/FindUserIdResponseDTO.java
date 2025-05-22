package com.example.demo.domain.user.dto.response;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class FindUserIdResponseDTO {
    private String userId;
    private String userNm;
    private Long userTypeCd;
    private LocalDateTime userCreatedAtDtm;
}