package com.example.demo.domain.project.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.company.dto.request.BaseRequest;
import com.example.demo.domain.project.dto.request.CompanyFilterRequest;
import com.example.demo.domain.project.dto.request.ProjectApplyRequest;
import com.example.demo.domain.project.dto.request.ProjectCreateRequest;
import com.example.demo.domain.project.dto.request.ProjectSearchRequest;
import com.example.demo.domain.project.dto.request.ScrapRequest;
import com.example.demo.domain.project.dto.response.AreaInfoResponse;
import com.example.demo.domain.project.dto.response.GroupSkillInfoResponse;
import com.example.demo.domain.project.dto.response.ProjectDetailResponse;
import com.example.demo.domain.project.dto.response.ProjectFormDataResponse;
import com.example.demo.domain.project.dto.response.ProjectListResponse;
import com.example.demo.domain.project.dto.response.ProjectRecruitStatus;
import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectApplicationEntity;
import com.example.demo.domain.project.service.ProjectService;
import com.example.demo.domain.project.vo.ProjectSummary;
import com.example.demo.domain.user.util.JwtAuthenticationToken;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
@CrossOrigin(value = "http://localhost:8504")
public class ProjectController {
	private final ProjectService projectService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<Void>> postProject(@Valid @RequestBody ProjectCreateRequest request,
			Authentication authentication){
		JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
		projectService.createProject(request, token);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 생성 성공", null));
	}
	
	@GetMapping
	public ResponseEntity<ApiResponse<ProjectListResponse>> getProjectList(Authentication authentication, @ModelAttribute ProjectSearchRequest request){
		JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 목록 조회 성공", projectService.fetchAllProject(token, request)));
	}
	
	@GetMapping("/companies")
	public ResponseEntity<ApiResponse<ProjectListResponse>> getCompanyProjectList(@ModelAttribute CompanyFilterRequest request, Authentication authentication){
		JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "기업 프로젝트 목록 조회 성공", projectService.fetchCompanyProject(request, token)));
	}
	
	@GetMapping("/companies/status")
	public ResponseEntity<ApiResponse<ProjectRecruitStatus>> getCompanyProjectStatusList(@ModelAttribute CompanyFilterRequest request, Authentication authentication){
		JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "기업 프로젝트 상태 조회 성공", projectService.fetchCompanyProjectCount(request, token)));
	}


	
	@PatchMapping
	public ResponseEntity<ApiResponse<Void>> patchProject(@RequestBody ProjectCreateRequest request){
		projectService.updateProject(request);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 수정 성공", null)); 
	}
	
	@DeleteMapping("/{projectSq}")
	public ResponseEntity<ApiResponse<Void>> deleteProject(@PathVariable("projectSq") Long projectSq){
		projectService.softDeleteProject(projectSq);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 삭제 성공", null)); 
	}
	
	@PostMapping("/{projectSq}/applications")
	public ResponseEntity<ApiResponse<Void>> applyProject(@PathVariable("projectSq") Long projectSq
			, @RequestBody ProjectApplyRequest applyRequest
			, @AuthenticationPrincipal Long userSq){
		projectService.createProjectApplication(projectSq, applyRequest, userSq);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 지원 성공", null));
	}
	
	@PostMapping("/{projectSq}/scraps")
	public ResponseEntity<ApiResponse<Void>> applyProject(@PathVariable("projectSq") Long projectSq
			, @RequestBody ScrapRequest scrapRequest
			, @AuthenticationPrincipal Long userSq){
		projectService.toggleProjectScrap(projectSq, scrapRequest, userSq);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 스크랩 토글 성공", null));
	}
	
	@GetMapping("/{projectSq}/details")
	public ResponseEntity<ApiResponse<ProjectDetailResponse>> getProjectDetails(
			@PathVariable("projectSq") Long projectSq, Authentication authentication){
		JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 상세 내역 반환 성공", projectService.fetchProject(projectSq, token)));
	}
	
	@GetMapping("/forms")
	public ResponseEntity<ApiResponse<ProjectFormDataResponse>> getProjectFormDatas(
	    @RequestParam(value = "projectSq", defaultValue = "0") Long projectSq) {
	    
	    return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 폼 데이터 반환 성공", projectService.fetchProjectFormDatas(projectSq)));
	}
	
	@GetMapping("/{areaCodeSq}/districts")
	public ResponseEntity<ApiResponse<List<AreaInfoResponse>>> getDistrictInfos(@PathVariable("areaCodeSq") Long areaCodeSq){
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "하위 행정구역 반환 성공", projectService.fetchDistricts(areaCodeSq)));
	}
	
	@GetMapping("/filters")
	public ResponseEntity<ApiResponse<List<?>>> getSearchFilterInfos(@RequestParam("type") String type){
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "프로젝트 필터 내용 반환 성공", projectService.fetchFilterInfos(type)));
	}
	
	
	
	
}
