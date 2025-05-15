package com.example.demo.domain.project.dto.response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.util.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProjectDetailResponse {

    private String projectTtl;                    
    private String companyNm;                    
    private String projectDetail;                 

    private String interviewStartDt;              
    private String interviewEndDt;                
    private String projectRecruitStartDt;        
    private String projectRecruitEndDt;           
    private String projectStartDt;                
    private String projectEndDt;                  

    private Integer projectViewCnt;               
    private Integer projectScrapCnt;               

    private String projectAddress;                
    private String projectExperience;              
    private String projectEducation;               

    private List<String> projectRequiredSkills;    
    private List<String> projectPreferredSkills;   
    private List<String> projectPreferredEtc;      

    private Long projectSalary;                   
    private List<String> projectJobRole;          
    private List<String> projectWorkType;         

    private String isScrap;                        
    private Long userSq;      
    
    public static ProjectDetailResponse from(Project p, Map<String, LocalDateTime> interviewTimes, String companyNm, String address, List<String> requiredSkillStrings, List<String> preferredSkillStrings
    		, List<String> contractTypes, List<String> jobs, String devGradeNm, String eduLvlNm) {
    	return ProjectDetailResponse.builder()
                .projectTtl(p.getProjectTtl())
                .companyNm(companyNm)
                .projectDetail(p.getProjectDescriptionTxt())

                .interviewStartDt(DateUtil.formatLocalDate(interviewTimes.get("minTime")))
                .interviewEndDt(DateUtil.formatLocalDate(interviewTimes.get("maxTime")))
                .projectRecruitStartDt(p.getProjectRecruitStartDt().toString())
                .projectRecruitEndDt(p.getProjectRecruitEndDt().toString())
                .projectStartDt(p.getProjectStartDt().toString())
                .projectEndDt(p.getProjectEndDt().toString())

                .projectViewCnt(p.getProjectViewCnt())
                .projectScrapCnt(p.getProjectScrapCnt())

                .projectAddress(address)
                .projectExperience(devGradeNm)
                .projectEducation(eduLvlNm)

                .projectRequiredSkills(requiredSkillStrings)
                .projectPreferredSkills(preferredSkillStrings)
                .projectPreferredEtc(new ArrayList<>())

                .projectSalary(p.getProjectSalary())
                .projectJobRole(jobs)
                .projectWorkType(contractTypes)

                .isScrap("N")
                .userSq(1L)
                .build();
    }
}
