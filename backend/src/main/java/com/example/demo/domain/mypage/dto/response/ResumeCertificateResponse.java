package com.example.demo.domain.mypage.dto.response;

import lombok.Data;

@Data
public class ResumeCertificateResponse {
	  private Long certificationSq;         // 자격증 순번 (PK)
	   private Long resumeSq;                // 이력서 순번 (FK)
	   private String certificationNm;       // 자격증 이름
	   private String certificationIssuerNm; // 발행 기관 이름
}
