package com.example.demo.domain.company.controller;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.company.dto.request.CompanyMemberSearchRequest;
import com.example.demo.domain.company.dto.request.CompanyStatusRequest;
import com.example.demo.domain.company.dto.response.CompanyMemberResponse;
import com.example.demo.domain.company.service.CompanyService;
import com.example.demo.domain.user.util.JwtAuthenticationToken;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
	
	private final CompanyService companyService;

	@GetMapping
	public ResponseEntity<ApiResponse<CompanyMemberResponse>> getCompanyMemberList(
			Authentication authentication,
			@ModelAttribute CompanyMemberSearchRequest request){
		JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 인원 목록 반환 성공", companyService.fetchMemberList(token, request)));
	}
	
	@PatchMapping("/{companySq}")
	public ResponseEntity<ApiResponse<Void>> patchMemberStatus(
			Authentication authentication,
			@PathVariable("companySq") Long companySq
			, @RequestBody CompanyStatusRequest request){
		companyService.updateMemberStatus(companySq, request);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 인원 상태 변경 성공", null));
	}
	
	
}
