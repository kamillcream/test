package com.example.demo.domain.project.dto.response;


import java.util.List;

import com.example.demo.domain.project.entity.Project;

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
	
	public static ProjectSummary from(Project project, String companyNm, String address,
			int remainingDay, List<String> reqSkills, String devGradeNm, String eduLvlNm
			) {
		return ProjectSummary.builder()
				.projectSq(project.getProjectSq())
				.projectTtl(project.getProjectTtl())
				.imageUrl(project.getProjectImageUrl())
				.viewCnt(project.getProjectViewCnt())
				.salary(project.getProjectSalary())
				.companyNm(companyNm)
				.address(address)
				.remainingDay(remainingDay)
				.reqSkills(reqSkills)
				.devGradeNm(devGradeNm)
				.requiredEduLvl(eduLvlNm)
				.build();
	}
}
