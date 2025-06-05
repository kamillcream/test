package com.example.demo.domain.mypage.controller;


import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.affiliation.service.AffiliationService;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.domain.affiliation.dto.response.*;
import com.example.demo.domain.affiliation.dto.request.*;
import com.example.demo.domain.affiliation.entity.*;

import lombok.RequiredArgsConstructor;

import javax.lang.model.type.NullType;
import java.util.*;


@RestController
@RequestMapping("/mypage/applications")
@RequiredArgsConstructor
public class ApplicationController {

    private final AffiliationService affiliationService;
    
    // 소속 공고 하나 조회
    @GetMapping("/{companySq}")
    public ResponseEntity<ApiResponse<AffiliationResponse>> getAffiliation(@PathVariable("companySq") Long companySq) {
    	return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 지원 상태 수정이 완료되었습니다.", affiliationService.getAffiliaion(companySq)));
    }
    
    
    // 회사
    // 소속 공고 지원자 현황 목록 조회
    @GetMapping("/company")
    public ResponseEntity<ApiResponse<ApplicantListResponse>> getAppliesByCompanySq(
    		@AuthenticationPrincipal Long userSq,
    		@RequestParam(value = "searchType", required = false) String searchType, 
    		@RequestParam(value = "keyword", required = false) String keyword,
    		@RequestParam(value = "readType", required = false) String readType,
    		@RequestParam(value = "page", defaultValue = "1") Long page,
    		@RequestParam(value = "size", defaultValue = "10") Long size) {
    	return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 공고 지원 현황 목록 조회가 완료되었습니다.", affiliationService.getAppliesByCompanySq(userSq, searchType, keyword, readType, page, size)));
    }
    
    // 열람 상태 변경
    @PutMapping("/read/{companyApplicationSq}")
    public ResponseEntity<ApiResponse<NullType>> updateApplicationReadAt(@PathVariable("companyApplicationSq") Long companyApplicationSq) {
    	affiliationService.updateApplicationReadAt(companyApplicationSq);
    	return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 지원 신청 열람이 완료되었습니다.", null));
    }
    
    // 합격 또는 불합격 변경
    @PutMapping("/apply/{companyApplicationSq}")
    public ResponseEntity<ApiResponse<NullType>> updateApplicationStatus(@PathVariable("companyApplicationSq") Long companyApplicationSq, @RequestBody CompanyApplication companyApplication) {
    	affiliationService.updateApplicationStatus(companyApplicationSq, companyApplication.getCompanyApplicationStatusCd());
    	return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 지원 상태 수정이 완료되었습니다.", null));
    }
    
    
    // 개인
    // 소속 공고 지원 현황 목록 조회
    @GetMapping("/user")
    public ResponseEntity<ApiResponse<ApplicationListResponse>> getAppliesByUserSq(
    		@AuthenticationPrincipal Long userSq,
    		@RequestParam(value = "searchType", required = false) String searchType, 
    		@RequestParam(value = "keyword", required = false) String keyword,
    		@RequestParam(value = "readType", required = false) String readType,
    		@RequestParam(value = "page", defaultValue = "1") Long page,
    		@RequestParam(value = "size", defaultValue = "10") Long size) {
    	System.out.println(userSq);
    	return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 공고 지원 현황 목록 조회가 완료되었습니다.", affiliationService.getAppliesByUserSq(userSq, searchType, keyword, readType, page, size)));
    }
    
    // 소속 공고 지원 취소
    @PatchMapping("/{companyApplicationSq}")
    public ResponseEntity<ApiResponse<NullType>> deleteApplication(@PathVariable("companyApplicationSq") Long companyApplicationSq) {
    	affiliationService.deleteApplication(companyApplicationSq);
    	return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 공고 지원이 취소되었습니다.", null));
    }
    
    // 소속 공고 스크랩 리스트
    @GetMapping("/scraps")
    public ResponseEntity<ApiResponse<AffiliationListResponse>> getScraps(
    		@AuthenticationPrincipal Long userSq,
    		@RequestParam(value = "searchType", required = false) String searchType, 
    		@RequestParam(value = "keyword", required = false) String keyword,
    		@RequestParam(value = "page", defaultValue = "1") Long page,
    		@RequestParam(value = "size", defaultValue = "10") Long size) {
    	return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 공고 스크랩 리스트 조회가 완료되었습니다.", affiliationService.getScraps(userSq, searchType, keyword, page, size)));
    }
    
    // 소속 공고 신청 내역 상세
    @GetMapping("/detail/{companyApplicationSq}")
    public ResponseEntity<ApiResponse<NullType>> getApply(@PathVariable("companyApplicationSq") Long companyApplicationSq) {
    	affiliationService.getApply(companyApplicationSq);
    	return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "소속 공고 지원 현황 상세 조회가 완료되었습니다.", null));
    }
    

}