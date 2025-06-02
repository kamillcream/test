package com.example.demo.domain.mypage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.dto.response.ResumeListResponse;
import com.example.demo.domain.mypage.dto.response.ResumeSkillDataResponse;
import com.example.demo.domain.mypage.mapper.MypageAddressMapper;
import com.example.demo.domain.mypage.service.ResumeService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mypage/resume")
@RequiredArgsConstructor

@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;
    private final MypageAddressMapper addressMapper;
    
    //이력서 등록
    @PostMapping("/new")
    public ResponseEntity<?> registerResume(@RequestBody ResumeRegisterRequest request) {
        try {
            System.out.println("받은 이력서 등록 요청: " + request);

            // 기본값 설정 (로그인 연동 전까지 임시)
            request.setUserSq(18L);

            // 시/도 명칭 정규화
            String normalizedSido = normalizeSido(request.getSido());

            // 지역코드 조회
            Long parentCode = addressMapper.selectParentAreaCodeBySido(normalizedSido);
            Long areaCodeSq = addressMapper.selectAreaCodeBySigunguAndParent(request.getSigungu(), parentCode);

            if (areaCodeSq == null) {
                throw new IllegalArgumentException("지역코드 조회 실패: " + normalizedSido + " " + request.getSigungu());
            }

            // addressSq 설정
            request.setAddressSq(areaCodeSq);

            // 실제 등록 처리
            resumeService.registerResume(request);

            return ResponseEntity.ok("등록 완료");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("서버 오류: " + e.getMessage());
        }
    }

    // 시/도 명칭 변환 함수
    private String normalizeSido(String sido) {
        if (sido == null) return null;
        switch (sido.trim()) {
            case "서울": return "서울전체";
            case "경기": return "경기전체";
            case "부산": return "부산전체";
            case "인천": return "인천전체";
            case "대구": return "대구전체";
            case "광주": return "광주전체";
            case "대전": return "대전전체";
            case "울산": return "울산전체";
            case "강원": return "강원전체";
            case "충북": return "충북전체";
            case "충남": return "충남전체";
            case "전북": return "전북전체";
            case "전남": return "전남전체";
            case "경북": return "경북전체";
            case "경남": return "경남전체";
            case "제주": return "제주전체";
            case "세종": return "세종특별자치시";
            default: return sido;
        }
    }


//	@PostMapping("/new")
//	public ResponseEntity<?> registerResume(@RequestBody ResumeRegisterRequest request) {
//	    System.out.println("받은 이력서 등록 요청: " + request);
//
//	    // ✅ userSq 기본값 세팅 (로그인 연동 전용 임시)
//	    request.setUserSq(18L);
//
//	    // ✅ 지역코드 조회 로직
//	    Long parentCode = addressMapper.selectParentAreaCodeBySido(request.getSido());
//	    Long areaCodeSq = addressMapper.selectAreaCodeBySigunguAndParent(request.getSigungu(), parentCode);
//	    if (areaCodeSq == null) {
//	        throw new IllegalArgumentException("지역코드 조회 실패: " + request.getSido() + " " + request.getSigungu());
//	    }
//	    request.setAddressSq(areaCodeSq);
//
//	    // ✅ 서비스 호출
//	    resumeService.registerResume(request);
//
//	    // ✅ 결과 응답
//	    return ResponseEntity.ok("등록 완료");
//	}
//	
	//대표 이력서 설정
	@PatchMapping("/representative/{resumeSq}")
	public ResponseEntity<String> setMainResume(@PathVariable("resumeSq") Long resumeSq) {
		Long userSq = 18L;
		resumeService.setMainResume(resumeSq, userSq);
	    return ResponseEntity.ok("대표 이력서 설정 완료");
	}
	
	//이력서 조회
	@GetMapping("/list")
	public ResponseEntity<List<ResumeListResponse>> getAllResumes() {
		Long userSq = 18L;
	    List<ResumeListResponse> resumes = resumeService.getAllResumes(userSq);
	    return ResponseEntity.ok(resumes);
	}
	
	//이력서 상세조회
	@GetMapping("/detail/{resumeSq}")
	 public ResponseEntity<ResumeRegisterRequest> getResumeById(@PathVariable("resumeSq") Long resumeSq) {
        ResumeRegisterRequest resume = resumeService.getResumeById(resumeSq);
        return ResponseEntity.ok(resume);
	}
	//이력서 삭제
	@PatchMapping("/{resumeSq}/delete")
	public ResponseEntity<String> deleteResume(@PathVariable("resumeSq") Long resumeSq){
		resumeService.softDeleteResume(resumeSq);
		return ResponseEntity.ok("이력서 삭제 완료");
	}
	//이력서 기술 태그
	@GetMapping("/skills")
	public ResponseEntity<ApiResponse<List<ResumeSkillDataResponse>>> getSkillList() {
	    List<ResumeSkillDataResponse> skills = resumeService.getGroupedSkills();
	    return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "기술 스택 조회 성공", skills));
	}
}
