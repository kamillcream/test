package com.example.demo.domain.mypage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import org.springframework.http.ResponseEntity;
import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.dto.response.ResumeListResponse;
import com.example.demo.domain.mypage.service.ResumeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


//import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mypage/resume")
public class ResumeController {
	
	private final ResumeService resumeService;
	
    // 생성자 직접 작성
    public ResumeController(ResumeService resumeService) {
        this.resumeService = resumeService;
    }
    //이력서 등록
	@PostMapping("/register")
	public String register(@RequestBody ResumeRegisterRequest request) {
		request.setUserSq(1L);//로그인 연동 시 바꾸기
		resumeService.registerResume(request);
		return "success";
	}
	//이력서 조회
	@GetMapping("/list")
	public ResponseEntity<List<ResumeListResponse>> getAllResumes() {
	    List<ResumeListResponse> resumes = resumeService.getAllResumes();
	    return ResponseEntity.ok(resumes);
	}
	//이력서 상세조회
	@GetMapping("/detail/{resumeSq}")
	 public ResponseEntity<ResumeRegisterRequest> getResumeById(@PathVariable("resumeSq") Long resumeSq) {
        ResumeRegisterRequest resume = resumeService.getResumeById(resumeSq);
        return ResponseEntity.ok(resume);
	}
}
