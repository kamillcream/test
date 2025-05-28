package com.example.demo.domain.project.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.LongBinaryOperator;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.common.ParentCodeEnum;
import com.example.demo.common.mapper.CommonCodeMapper;
import com.example.demo.domain.company.mapper.CompanyMapper;
import com.example.demo.domain.mypage.mapper.ResumeCareerMapper;
import com.example.demo.domain.mypage.mapper.ResumeMapper;
import com.example.demo.domain.mypage.mapper.ResumeSkillMapper;
import com.example.demo.domain.mypage.vo.ResumeVo;
import com.example.demo.domain.project.dto.request.ApplicationSqRequest;
import com.example.demo.domain.project.dto.request.ApplicationStatusRequest;
import com.example.demo.domain.project.dto.response.ApplicationStatusList;
import com.example.demo.domain.project.dto.response.ApplicationStatusResponse;
import com.example.demo.domain.project.dto.response.GroupSkillInfoResponse;
import com.example.demo.domain.project.dto.response.ProjectAppListResponse;
import com.example.demo.domain.project.dto.response.SingleSkillInfoResponse;
import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectApplicationEntity;
import com.example.demo.domain.project.mapper.ProjectApplicationMapper;
import com.example.demo.domain.project.mapper.ProjectMapper;
import com.example.demo.domain.project.vo.ApplicationStatusVo;
import com.example.demo.domain.project.vo.ApplicationSummary;
import com.example.demo.domain.project.vo.ResumeNmTtlVo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectApplicationService {
	private final ProjectApplicationMapper applicationMapper;
	private final CommonCodeMapper commonCodeMapper;
	private final ResumeMapper resumeMapper;
	private final ResumeCareerMapper resumeCareerMapper;
	private final ResumeSkillMapper resumeSkillMapper;
	private final CompanyMapper companyMapper; 
	

	public List<ApplicationSummary> fetchProjectApplications(Long userSq) {
		List<ApplicationSummary> summaries = applicationMapper.findApplicationSummariesByUserSq(userSq);
		
		return summaries;
	}
	
	
	public void updateApplicantResult(ApplicationStatusRequest request, Long applicationSq) {
		Long statusCd = commonCodeMapper.findCommonCodeSqByName(request.getStatus(), ParentCodeEnum.PRO_APPLICATION.getCode());
		applicationMapper.updateApplicationStatus(statusCd, applicationSq);
	}
	
	public void updateInterviewTimeSelected(Long interviewTimeSq, ApplicationSqRequest request) {
		applicationMapper.updateInterviewTimeSelected(interviewTimeSq);
		applicationMapper.updateApplicationInterviewTimeAndStatus(request.getApplicationSq(), applicationMapper.findInterviewTimeBySq(interviewTimeSq));
	}
	
	public List<ApplicationStatusList> fetchProjectApplicationsByProject(Long projectSq) {
		List<Long> applicationSqs = applicationMapper.findAllSqByProjectSq(projectSq);
		List<ApplicationStatusResponse> responses = new ArrayList<>();
		applicationSqs.forEach(
				s -> {
					Long resumeSq = applicationMapper.findResumeBySq(s);
					Long appCompanySq = applicationMapper.findCompanyBySq(s);
					String memberType = applicationMapper.findMmTypStrBySq(s);
					ApplicationStatusVo applicationStatusVo = applicationMapper.findStatusVoByAppSq(s);
					List<String> skills = resumeSkillMapper.findAllNmBySq(resumeSq);
					ResumeNmTtlVo resumeNmTtlVo = resumeMapper.findResumeNmTtlBySq(resumeSq);
					int careerYear = resumeCareerMapper.calculateCareerByResSq(resumeSq);
					if (memberType.equals("기업")) {
						String companyNm = companyMapper.findCompanyNmByCompanySq(appCompanySq);
						responses.add(ApplicationStatusResponse.company(s, resumeNmTtlVo, careerYear, skills, applicationStatusVo, memberType, companyNm));
					}
					else {
						responses.add(ApplicationStatusResponse.personal(s, resumeNmTtlVo, careerYear, skills, applicationStatusVo, memberType));
					}
				}
		);
		
		return groupByMemberType(responses);
		
	}
	
	public List<ApplicationStatusList> groupByMemberType(List<ApplicationStatusResponse> responses) {
        return responses.stream()
                .collect(Collectors.groupingBy(
                        ApplicationStatusResponse::getMemberType
                ))
                .entrySet()
                .stream()
                .map(entry -> {
                    ApplicationStatusList grouped = new ApplicationStatusList();
                    grouped.setApplicantType(entry.getKey());
                    grouped.setResponse(entry.getValue());
                    return grouped;
                })
                .collect(Collectors.toList());
    }
	
	
}
