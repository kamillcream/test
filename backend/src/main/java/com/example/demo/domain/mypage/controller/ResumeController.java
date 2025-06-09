package com.example.demo.domain.mypage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.dto.response.ResumeListResponse;
import com.example.demo.domain.mypage.dto.response.ResumeRegisterResponse;
import com.example.demo.domain.mypage.dto.response.ResumeSkillDataResponse;
import com.example.demo.domain.mypage.dto.response.ResumeSkillPairResponse;
import com.example.demo.domain.mypage.mapper.MypageAddressMapper;
import com.example.demo.domain.mypage.service.ResumeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mypage/resume")
@RequiredArgsConstructor

public class ResumeController {

    private final ResumeService resumeService;
    private final MypageAddressMapper addressMapper;
    
    //이력서 등록
    @PostMapping("/new")
            public ResponseEntity<ApiResponse<ResumeRegisterResponse>> registerResume(
                @AuthenticationPrincipal Long userSq, @RequestBody ResumeRegisterRequest request
	) {
                System.out.println("userSq = " + userSq);

                if (userSq == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.of(HttpStatus.UNAUTHORIZED, "로그인이 필요합니다.", null));
                }
                
                request.setUserSq(userSq);
                System.out.println("📥 받은 이력서 등록 요청:" + request);
                System.out.println("✅ DTO 내부 userSq = " + request.getUserSq()); 

            	try {
                    ResumeRegisterResponse response = resumeService.registerResume(request);
                    return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "이력서 등록 성공", response));
                } catch (IllegalArgumentException e) {
                    return ResponseEntity
                        .status(HttpStatus.BAD_REQUEST)
                        .body(ApiResponse.of(HttpStatus.BAD_REQUEST, e.getMessage(), null));
                } catch (Exception e) {
                    return ResponseEntity
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "서버 오류가 발생했습니다.", null));
                }
            }

	//대표 이력서 설정
	@PatchMapping("/representative/{resumeSq}")
	public ResponseEntity<ApiResponse<String>>setMainResume (@AuthenticationPrincipal Long userSq, @PathVariable("resumeSq") Long resumeSq) {
		resumeService.setMainResume(resumeSq, userSq);
	    return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "대표 이력서 설정 완료", "success"));
	}
	
	//이력서 조회
	@GetMapping("/list")
	public ResponseEntity<ApiResponse<List<ResumeListResponse>>> getAllResumes(@AuthenticationPrincipal Long userSq) {
	    List<ResumeListResponse> resumes = resumeService.getAllResumes(userSq);
	    return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "이력서 조회가 완료되었습니다.", resumes));
	}
	
	//이력서 상세조회
	@GetMapping("/detail/{resumeSq}")
	 public ResponseEntity<ApiResponse<ResumeRegisterRequest>> getResumeById(@PathVariable("resumeSq") Long resumeSq) {
        ResumeRegisterRequest resume = resumeService.getResumeById(resumeSq);
        if (resume == null) {
            System.out.println("resume is null for resumeSq=" + resumeSq);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.of(HttpStatus.NOT_FOUND, "이력서가 없습니다.", null));
        }
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "이력서 상세 조회가 완료되었습니다.", resume));
	}
	//이력서 삭제
	@PatchMapping("/{resumeSq}/delete")
	public ResponseEntity<ApiResponse<String>> deleteResume(@PathVariable("resumeSq") Long resumeSq){
		resumeService.softDeleteResume(resumeSq);
		return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "이력서 삭제 완료", "success"));
	}
	//이력서 기술 태그
	@GetMapping("/skills")
	public ResponseEntity<ApiResponse<List<ResumeSkillDataResponse>>> getAllSkillTags() {
	    List<ResumeSkillDataResponse> skills = resumeService.getAllSkillTags();
	    return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, " ", skills));
	}
}
