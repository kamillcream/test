package com.example.demo.domain.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.mypage.dto.response.ResumeSkillPairResponse;

@Mapper

public interface ResumeSkillMapper {
	List<ResumeSkillPairResponse> findSkillInfoList();

}
