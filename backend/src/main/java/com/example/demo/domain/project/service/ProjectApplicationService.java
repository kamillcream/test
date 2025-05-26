package com.example.demo.domain.project.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.common.ParentCodeEnum;
import com.example.demo.common.mapper.CommonCodeMapper;
import com.example.demo.domain.company.mapper.CompanyMapper;
import com.example.demo.domain.mypage.mapper.ResumeMapper;
import com.example.demo.domain.mypage.vo.ResumeVo;
import com.example.demo.domain.project.dto.request.ApplicationSqRequest;
import com.example.demo.domain.project.dto.request.ApplicationStatusRequest;
import com.example.demo.domain.project.dto.response.ProjectAppListResponse;
import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectApplicationEntity;
import com.example.demo.domain.project.mapper.ProjectApplicationMapper;
import com.example.demo.domain.project.mapper.ProjectMapper;
import com.example.demo.domain.project.vo.ApplicationSummary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectApplicationService {
	private final ProjectApplicationMapper applicationMapper;
	
	private final CommonCodeMapper commonCodeMapper;

	public List<ApplicationSummary> fetchProjectApplications(Long userSq) {
		List<ApplicationSummary> summaries = applicationMapper.findApplicationSummariesByUserSq(userSq);
		
		return summaries;
	}
	
	public void updateApplicantResult(ApplicationStatusRequest request, Long applicationSq) {
		Long statusCd = commonCodeMapper.findCommonCodeSqByName(request.getStatus(), ParentCodeEnum.APPLICATION.getCode());
		applicationMapper.updateApplicationStatus(statusCd, applicationSq);
	}
	
	public void updateInterviewTimeSelected(Long interviewTimeSq, ApplicationSqRequest request) {
		applicationMapper.updateInterviewTimeSelected(interviewTimeSq);
		applicationMapper.updateApplicationInterviewTime(request.getApplicationSq(), applicationMapper.findInterviewTimeBySq(interviewTimeSq));
	}
	
	
}
