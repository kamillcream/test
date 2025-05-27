package com.example.demo.domain.mypage.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data

public class ResumeEducationRequest {
	private String schoolNm;         // 학교명
	private String majorNm;          // 전공
    private LocalDate admissionDt;      // 입학일자
    private LocalDate graduationDt;     // 졸업일자
    private Long statusCd;           // 졸업상태 코드

}



