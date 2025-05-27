package com.example.demo.domain.mypage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResumeSkillMapper {
	public List<String> findAllNmBySq(Long resumeSq);
}
