package com.example.demo.domain.project.vo;

import java.time.LocalDateTime;

import com.example.demo.domain.mypage.vo.ResumeVo;
import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectApplicationEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationSummary {
	private String projectTitle;
	private String companyTitle;
	private LocalDateTime appliedDt;
	private Long resumeSq;
	private String resumeTitle;
	private Integer applicantCnt;
	private LocalDateTime readApplicationDt;
	private String applicantType;
}
