package com.example.demo.domain.mypage.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.mapper.ResumeMapper;
import com.example.demo.domain.mypage.dto.response.ResumeListResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeService {
	
	private final ResumeMapper resumeMapper;
	public void registerResume(ResumeRegisterRequest request) {
		resumeMapper.insertResume(request);
	}
	public ResumeRegisterRequest getResumeById(Long resumeSq) {
	    return resumeMapper.selectResumeById(resumeSq);
	}
//	public List<ResumeRegisterRequest> getAllResumes() {
//		 return resumeMapper.selectAllResumes();
//	}
	public List<ResumeListResponse> getAllResumes() {
	    Long userSq = 1L;
	    return resumeMapper.selectAllResumesByUserSq(userSq);
	}
}

