package com.example.demo.domain.company.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.domain.project.vo.ResumeNmTtlVo;

import lombok.*;

@AllArgsConstructor
@Getter
@Builder
public class CompanyMemberVo {
	private final Long userSq;
	private final String userNm;
	private final String resumeTtl;
	
	private final LocalDate careerStartDt;
	private final LocalDate careerEndDt;
	
	private final List<String> skillTagNms;
	
	private final Integer careerYr;
	
	
	public static CompanyMemberVo from(Long userSq, ResumeNmTtlVo resumeNmTtlVo, LocalDate careerStart, LocalDate careerEnd
			, List<String> skillTags, Integer careerYr) {
		return CompanyMemberVo.builder()
				.userSq(userSq)
				.userNm(resumeNmTtlVo.getResumeNm())
				.resumeTtl(resumeNmTtlVo.getResumeTtl())
				.careerStartDt(careerStart)
				.careerEndDt(careerEnd)
				.skillTagNms(skillTags)
				.careerYr(careerYr)
				.build();
	}
	
}
