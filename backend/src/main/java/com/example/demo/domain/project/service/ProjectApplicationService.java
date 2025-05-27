package com.example.demo.domain.project.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import com.example.demo.domain.project.dto.response.ProjectAppListResponse;
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
	private final ProjectMapper projectMapper;
	private final ResumeMapper resumeMapper;
	private final ResumeCareerMapper resumeCareerMapper;
	private final ResumeSkillMapper resumeSkillMapper;
	

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
		applicationMapper.updateApplicationInterviewTime(request.getApplicationSq(), applicationMapper.findInterviewTimeBySq(interviewTimeSq));
	}
	
	public List<ApplicationStatusResponse> fetchProjectApplicationsByCompany(Long companySq) {
		List<Long> applicationSqs = applicationMapper.findAllSqByCompanySq(companySq);
		List<ApplicationStatusResponse> responses = new ArrayList<>();
		applicationSqs.forEach(
				s -> {
					Long resumeSq = applicationMapper.findResumeBySq(s);
					String memberType = applicationMapper.findMmTypStrBySq(s);
					ApplicationStatusVo applicationStatusVo = applicationMapper.findStatusVoByCompanySq(companySq);
					List<String> skills = resumeSkillMapper.findAllNmBySq(resumeSq);
					ResumeNmTtlVo resumeNmTtlVo = resumeMapper.findResumeNmTtlBySq(resumeSq);
					int careerYear = resumeCareerMapper.calculateCareerByResSq(resumeSq);
					responses.add(ApplicationStatusResponse.from(s, resumeNmTtlVo, careerYear, skills, applicationStatusVo, memberType));
				}
		);
		
		return responses;
		
	}
	
	
}
