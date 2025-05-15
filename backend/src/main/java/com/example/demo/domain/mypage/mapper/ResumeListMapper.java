package com.example.demo.domain.mypage.mapper;

import com.example.demo.domain.mypage.dto.response.ResumeListResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ResumeListMapper {
    List<ResumeListResponse> getResumeListByMemberId(Long memberId);
}

