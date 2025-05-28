package com.example.demo.domain.mypage.mapper;

import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.vo.ResumeVo;
import com.example.demo.domain.project.vo.ResumeNmTtlVo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResumeMapper {
	void insertResume(ResumeRegisterRequest request);
	public List<ResumeVo> findResumeVoByUserSq(Long userSq);
	public ResumeNmTtlVo findResumeNmTtlBySq(Long resumeSq);
}
