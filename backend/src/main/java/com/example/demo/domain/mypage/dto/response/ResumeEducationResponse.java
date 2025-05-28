package com.example.demo.domain.mypage.dto.response;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResumeEducationResponse {
	    private String schoolNm;         // 학교명
	    private String majorNm;          // 전공
	    private LocalDate admissionDt;      // 입학일자
	    private LocalDate graduationDt;     // 졸업일자
	    private Long statusCd;           // 졸업상태 코드.
	}


