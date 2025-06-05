package com.example.demo.domain.mypage.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResumeRegisterResponse {
    private Long resumeSq;             // 생성된 이력서 PK
    private String resumeTtl;          // 이력서 제목
    private boolean representative;    // 대표 이력서 여부
} 
