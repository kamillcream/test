package com.example.demo.domain.mypage.mapper;

import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.dto.response.ResumeListResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResumeMapper {
	void insertResume(ResumeRegisterRequest request);
	ResumeRegisterRequest selectResumeById(Long resumeSq);
	//전체이력서 조회
	List<ResumeListResponse> selectAllResumesByUserSq(Long userSq);


}
