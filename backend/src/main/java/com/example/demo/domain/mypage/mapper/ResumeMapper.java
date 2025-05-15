package com.example.demo.domain.mypage.mapper;

import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResumeMapper {
	void insertResume(ResumeRegisterRequest request);
}
