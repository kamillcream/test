package com.example.demo.domain.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;

import com.example.demo.domain.project.dto.request.ApplicationSqRequest;
import com.example.demo.domain.project.dto.request.ApplicationStatusRequest;
import com.example.demo.domain.project.dto.request.ProjectApplyRequest;
import com.example.demo.domain.project.dto.response.ApplicationStatusList;
import com.example.demo.domain.project.dto.response.InterviewTimeInfoResponse;

import com.example.demo.domain.project.service.ProjectApplicationService;
import com.example.demo.domain.project.service.ProjectService;

import com.example.demo.domain.project.vo.ApplicationSummary;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects/applications")
@RequiredArgsConstructor
public class ProjectApplicationController {
	
	private final ProjectApplicationService projectApplicationService;
	private final ProjectService projectService;
	
	@GetMapping
	public ResponseEntity<ApiResponse<List<ApplicationSummary>>> getApplicationList(@AuthenticationPrincipal Long userSq){
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 지원 조회 성공", projectApplicationService.fetchProjectApplications(userSq)));
	}
	
	@GetMapping("/interviews/{projectSq}")
	public ResponseEntity<ApiResponse<List<InterviewTimeInfoResponse>>> getAvailableInterviewTimes(@PathVariable("projectSq") Long projectSq){
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "인터뷰 가능 시간 조회 성공", projectService.fetchProjectAvailableTimes(projectSq)));
	}
	
	@GetMapping("/{projectSq}")
	public ResponseEntity<ApiResponse<List<ApplicationStatusList>>> getProjectApplicationsByCompanies(@PathVariable("projectSq") Long projectSq){
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 공고 지원자 목록 반환 성공", projectApplicationService.fetchProjectApplicationsByProject(projectSq)));
	}
	
	@PatchMapping("/{applicationSq}")
	public ResponseEntity<ApiResponse<Void>> patchApplicationStatus(@PathVariable("applicationSq")Long applicationSq, @RequestBody ApplicationStatusRequest request){
		projectApplicationService.updateApplicantResult(request, applicationSq);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 지원 상태 변경 성공", null));
	}
	
	@PatchMapping("/interviews/{interviewTimeSq}")
	public ResponseEntity<ApiResponse<Void>> patchApplicationStatus(@PathVariable("interviewTimeSq") Long interviewTimeSq, @RequestBody ApplicationSqRequest request){
		projectApplicationService.updateInterviewTimeSelected(interviewTimeSq, request);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "인터뷰 시간 선택 성공", null));
	}
	
	@PostMapping("/{projectSq}")
	public ResponseEntity<ApiResponse<Void>> applyProject(@PathVariable("projectSq") Long projectSq
			, @RequestBody ProjectApplyRequest applyRequest
			, @AuthenticationPrincipal Long userSq){
		projectService.createProjectApplication(projectSq, applyRequest, userSq);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 지원 성공", null));
	}
	
	
	
}
