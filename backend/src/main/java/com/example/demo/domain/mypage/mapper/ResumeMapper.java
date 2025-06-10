package com.example.demo.domain.mypage.mapper;

import com.example.demo.domain.mypage.dto.request.ResumeEducationRequest;
import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.dto.response.ResumeListResponse;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.example.demo.domain.mypage.vo.ResumeVo;
import com.example.demo.domain.project.vo.ResumeNmTtlVo;

import java.util.List;
import java.util.Map;

@Mapper
public interface ResumeMapper {
	void insertResume(ResumeRegisterRequest request);
	
	public ResumeNmTtlVo findLatestResumeBySq(Long resumeSq);
	public ResumeNmTtlVo findRepResumeNmTtlByUserSq(Long userSq);
	
	public List<Long> findResumesByUserSq(Long userSq);
	public Long findRepResumeByUserSq(Long userSq);
	public Long findLatestResumeSqByUserSq(Long userSq);

	ResumeRegisterRequest selectResumeById(@Param("resumeSq") Long resumeSq);
	Long selectAreaCodeBySigunguAndParent(Map<String, Object> params);

	//전체이력서 조회
	List<ResumeListResponse> selectAllResumes(@Param("userSq") Long userSq);
	
	//대표이력서 하나만
	void updateAllRepresentativeN(@Param("userSq") Long userSq);
    void updateRepresentativeY(@Param("resumeSq") Long resumeSq);
	
    //이력서 수정
    void updateResume(ResumeRegisterRequest request);
    
    //이력서 삭제
	void updateDeleteYn(@Param("resumeSq") Long resumeSq);

	
	//학력
	void insertEducation(ResumeEducationRequest request);
	void deleteEducationByResumeSq(Long resumeSq);
	
	public List<ResumeVo> findResumeVoByUserSq(@Param("userSq") Long userSq);
	public ResumeNmTtlVo findResumeNmTtlBySq(@Param("resumeSq") Long resumeSq);
}
