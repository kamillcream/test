package com.example.demo.domain.project.dto.response;


import java.util.List;

import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.util.ProjectUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class ProjectSummary {
	private Long projectSq;
	private String projectTtl;
	private String companyNm;
	private String address;
	private String imageUrl;
	private Integer remainingDay;
	private Integer viewCnt;
	private String requiredEduLvl;
	private String devGradeNm;
	private Long salary;
	private List<String> reqSkills;
	
	public static ProjectSummary from(Project project, ProjectUtil util, String address) {
		return ProjectSummary.builder()
				.projectSq(project.getProjectSq())
				.address(address)
				.projectTtl(project.getProjectTtl())
				.imageUrl(project.getProjectImageUrl())
				.viewCnt(project.getProjectViewCnt())
				.salary(project.getProjectSalary())
				.companyNm(util.convertCompanySqToName(project.getCompanySq()))
				.remainingDay(project.calcaulateRemainingDay(project.getProjectRecruitEndDt()))
				.reqSkills(util.fetchReqSkillsByProjectSq(project.getProjectSq()))
				.devGradeNm(util.convertCommonCodeSqToNm(project.getProjectDeveloperGradeCd()))
				.requiredEduLvl(util.convertCommonCodeSqToNm(project.getProjectRequiredEducationCd()))
				.build();
	}
}
