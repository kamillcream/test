package com.example.demo.domain.company.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.company.dto.CompanyMemberVo;
import com.example.demo.domain.company.dto.CompanyStatusRequest;
import com.example.demo.domain.company.service.CompanyService;
import com.example.demo.domain.project.dto.request.ScrapRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/companies")
public class CompanyController {
	
	private final CompanyService companyService;

	@GetMapping("/{companySq}")
	public ResponseEntity<ApiResponse<List<CompanyMemberVo>>> getCompanyMemberList(@PathVariable("companySq") Long companySq){
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 인원 목록 반환 성공", companyService.fetchMemberList(companySq)));
	}
	
	@PatchMapping("/{companySq}")
	public ResponseEntity<ApiResponse<Void>> patchMemberStatus(@PathVariable("companySq") Long companySq
			, @RequestBody CompanyStatusRequest request){
		companyService.updateMemberStatus(companySq, request);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 인원 상태 변경 성공", null));
	}
	
	
}
