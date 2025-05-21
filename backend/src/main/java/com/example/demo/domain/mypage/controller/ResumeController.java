package com.example.demo.domain.mypage.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.service.ResumeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/mypage/resume")
@RequiredArgsConstructor
public class ResumeController {
	
	private final ResumeService resumeService;
	
	@PostMapping("/register")
	public String register(@RequestBody ResumeRegisterRequest request) {
		request.setUserSq(1L);//로그인 연동 시 바꾸기
		resumeService.registerResume(request);
		return "success";
	}
}
