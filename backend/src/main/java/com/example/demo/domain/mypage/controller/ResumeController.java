package com.example.demo.domain.mypage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.dto.response.ResumeListResponse;
import com.example.demo.domain.mypage.dto.response.ResumeRegisterResponse;
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

public class ResumeController {

    private final ResumeService resumeService;
    private final MypageAddressMapper addressMapper;
    
    //이력서 등록
    @PostMapping("/new")
            public ResponseEntity<ApiResponse<ResumeRegisterResponse>> registerResume(@RequestBody ResumeRegisterRequest request) {
                System.out.println("받은 이력서 등록 요청: " + request);
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
//            // 기본값 설정 (로그인 연동 전까지 임시)
//            request.setUserSq(18L);
//
//            // 1. 지역코드 조회 
//            Long areaCodeSq = addressMapper.selectAreaCodeSqBySigungu(request.getSigungu());
//            if (areaCodeSq == null) {
//                throw new IllegalArgumentException("지역코드 조회 실패: " + request.getSigungu());
//            }
//
//            // 2. addressDTO 만들기
//            AddressDTO addressDTO = AddressDTO.builder()
//                    .zonecode(request.getZonecode())
//                    .address(request.getAddress())
//                    .detailAddress(request.getDetailAddress())
//                    .sigungu(request.getSigungu())
//                    .latitude(request.getLatitude())
//                    .longitude(request.getLongitude())
//                    .areaCodeSq(areaCodeSq)
//                    .build();
//
//            // 3. 주소 INSERT
//            addressMapper.insertAddress(addressDTO);
//            request.setAddressSq(addressDTO.getAddressSq()); // 주소 PK 반영
//
//            // 4. 이력서 등록
//            resumeService.registerResume(request);
//
//            return ResponseEntity.ok("등록 완료");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                                 .body("서버 오류: " + e.getMessage());
//        }
//    }
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
