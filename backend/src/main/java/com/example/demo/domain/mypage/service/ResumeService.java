package com.example.demo.domain.mypage.service;

import org.springframework.stereotype.Service;

import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.mapper.ResumeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeService {
	
	private final ResumeMapper resumeMapper;
	public void registerResume(ResumeRegisterRequest request) {
		resumeMapper.insertResume(request);
	}
}
