package com.example.demo.domain.project.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.util.DateUtil;
import com.example.demo.domain.project.util.ProjectUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class ExistProjectVo {
	private String projectTtl;
	private AreaInfoResponse parentDistrict;
	private AreaInfoResponse subDistrict;
	private String devGrade;
	private String educationLvl;
	private Long projectSalary;
	private List<String> contract;
	private List<String> jobs;
	private List<String> reqSkills;
	private List<String> preferSkills;
	private String projectStartDt;
	private String projectEndDt;
	private String recruitStartDt;
	private String recruitEndDt;
	private Map<String, List<String>> interviewTimes;
	private String preferredEtc;
	private String description;
	
	public static ExistProjectVo from(Project p, ProjectUtil util, List<String> reqSkills, 
			List<String> preferSkills, AreaInfoResponse parent, AreaInfoResponse sub) {
    	Long projectSq = p.getProjectSq();
    	Map<String, List<String>> interviewTimes = util.fetchAndConvertTimeSlots(projectSq);
    	return ExistProjectVo.builder()
                .projectTtl(p.getProjectTtl())
                .parentDistrict(parent)
                .subDistrict(sub)
                .projectSalary(p.getProjectSalary())
                .description(p.getProjectDescriptionTxt())
                .recruitStartDt(p.getProjectRecruitStartDt().toString())
                .recruitEndDt(p.getProjectRecruitEndDt().toString())
                .projectStartDt(p.getProjectStartDt().toString())
                .projectEndDt(p.getProjectEndDt().toString())
                .devGrade(util.convertCommonCodeSqToNm(p.getProjectDeveloperGradeCd()))
                .educationLvl(util.convertCommonCodeSqToNm(p.getProjectRequiredEducationCd()))
                .reqSkills(reqSkills)
                .preferSkills(preferSkills)
                .preferredEtc(p.getProjectPreferenceTxt())
                .jobs(util.fetchJobsByProjectSq(projectSq))
                .contract(util.fetchWorkTypesByProjectSq(projectSq))
                .interviewTimes(interviewTimes)
                .build();
    }
}
