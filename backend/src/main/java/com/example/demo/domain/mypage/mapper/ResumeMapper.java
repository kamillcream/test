package com.example.demo.domain.mypage.mapper;

import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.dto.response.ResumeListResponse;

import io.lettuce.core.dynamic.annotation.Param;

import org.apache.ibatis.annotations.Mapper;
import com.example.demo.domain.mypage.vo.ResumeVo;
import com.example.demo.domain.project.vo.ResumeNmTtlVo;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ResumeMapper {
	void insertResume(ResumeRegisterRequest request);
	ResumeRegisterRequest selectResumeById(Long resumeSq);
	Long selectAreaCodeBySigunguAndParent(Map<String, Object> params);

	//전체이력서 조회
	List<ResumeListResponse> selectAllResumes(Long userSq);
	
	//대표이력서 하나만
	void updateAllRepresentativeN(Long userSq);
    void updateRepresentativeY(Long resumeSq);
	
    //이력서 삭제.
	void updateDeleteYn(@Param("resumeSq")Long resumeSq);


	public List<ResumeVo> findResumeVoByUserSq(Long userSq);
	public ResumeNmTtlVo findResumeNmTtlBySq(Long resumeSq);
}
