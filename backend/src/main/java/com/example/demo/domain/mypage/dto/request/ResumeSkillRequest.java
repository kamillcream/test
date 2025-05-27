package com.example.demo.domain.mypage.dto.request;

import lombok.Data;

@Data
public class ResumeSkillRequest {
	private Long skillSq;  // 보유 기술 순번 (PK)
	private Long reumesSq; // 이력서 순번 (FK)
	private Long skillTagSq; // 기술 태그 순번
    private String skillTagNm; // 기술 태그 이름

}
