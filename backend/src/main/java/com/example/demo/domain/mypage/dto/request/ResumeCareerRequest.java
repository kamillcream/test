package com.example.demo.domain.mypage.dto.request;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ResumeCareerRequest {
	private Long careerSq;           // 경력 순번 (PK)
    private Long resumeSq;           // 이력서 순번 (FK)
    private String companyNm;        // 회사명
    private String departmentNm;     // 부서
    private String positionNm;       // 직급
    private LocalDate startDt;          // 입사일자 
    private String endDt;   

}
