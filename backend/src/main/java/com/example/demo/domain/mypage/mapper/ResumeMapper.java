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
	public ResumeNmTtlVo findRepResumeNmTtlBySq(Long resumeSq);
	public ResumeNmTtlVo findResumeNmTtlBySq(Long resumeSq);
	public ResumeNmTtlVo findRepResumeNmTtlByUserSq(Long userSq);
	
	public ResumeNmTtlVo findLatestResumeBySq(Long resumeSq);
	public List<Long> findResumesByUserSq(Long userSq);
	public Long findRepResumeByUserSq(Long userSq);
	public Long findLatestResumeSqByUserSq(Long userSq);
}
