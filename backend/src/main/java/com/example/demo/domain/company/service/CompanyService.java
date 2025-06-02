package com.example.demo.domain.company.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.security.web.webauthn.api.PublicKeyCose;
import org.springframework.stereotype.Service;

import com.example.demo.common.ParentCodeEnum;
import com.example.demo.common.mapper.CommonCodeMapper;
import com.example.demo.domain.company.dto.CompanyMemberVo;
import com.example.demo.domain.company.dto.CompanyStatusRequest;
import com.example.demo.domain.company.mapper.CompanyMapper;
import com.example.demo.domain.mypage.mapper.ResumeCareerMapper;
import com.example.demo.domain.mypage.mapper.ResumeMapper;
import com.example.demo.domain.mypage.mapper.ResumeSkillMapper;
import com.example.demo.domain.project.util.ProjectUtil;
import com.example.demo.domain.project.vo.ResumeNmTtlVo;
import com.example.demo.domain.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
	private final CompanyMapper companyMapper;
	private final CommonCodeMapper commonCodeMapper;

	
	private final ResumeMapper resumeMapper;
	private final ResumeCareerMapper resumeCareerMapper;
	private final ResumeSkillMapper resumeSkillMapper;
	
	
	public Long fetchCompanySq(Long userSq) {
		return companyMapper.findCompanySqByUserSq(userSq);
	}
	
	public Long fetchCompanySq(Long userSq, Long userTypeCd) {
		if (userTypeCd.equals(commonCodeMapper.findCommonCodeSqByEngName("COMPANY", ParentCodeEnum.MEMBER_TYPE.getCode()))) {
		    return companyMapper.findCompanySqByUserSq(userSq);
		} else {
		    throw new RuntimeException("접근 권한이 없습니다.");
		}
	}
	
	public Long fetchUserSq(Long companySq) {
		return companyMapper.findUserSqByCompanySq(companySq);
	}
	
	public String fetchCompanyBizNumByUser(Long userSq) {
		return companyMapper.findBizNumByUserSq(userSq);
	}
	
	public String fetchCompanyBizNumByCompany(Long companySq) {
		return companyMapper.findBizNumByCompanySq(companySq);
	}
	
	public List<CompanyMemberVo> fetchMemberList(Long companySq) {
		List<Long> companyUserSqs = companyMapper.findUserSqsByCompanySq(companySq);
		List<CompanyMemberVo> responses = new ArrayList<>();
		
		companyUserSqs.forEach(
				sq -> {
					Long repResumeSq = resumeMapper.findRepResumeByUserSq(sq);
					List<Long> resumeSqs = resumeMapper.findResumesByUserSq(sq);
					
					ResumeNmTtlVo resumeNmTtlVo = resumeMapper.findResumeNmTtlBySq(repResumeSq);
					
					LocalDate joinDt = companyMapper.findCompanyJoinDt(sq);
					LocalDate leaveDt = companyMapper.findCompanyLeaveDt(sq);
					
					Integer careerYr = calculateTotalCareer(resumeSqs);
					
					List<String> skillTags = resumeSkillMapper.findAllNmBySq(repResumeSq);
					
					responses.add(CompanyMemberVo.from(sq, resumeNmTtlVo, joinDt, leaveDt, skillTags, careerYr));
					
				}
		);
		
		return responses;
	}
	
	public void updateMemberStatus(Long companySq, CompanyStatusRequest request) {
		Long memberStatusCd = commonCodeMapper.findCommonCodeSqByName(request.getNewStatus(), ParentCodeEnum.EMPLOYMENT.getCode());
		companyMapper.updateMemberStatus(request.getUserSq(), memberStatusCd);
	}
	
	public Integer calculateTotalCareer(List<Long> resumeSqs) {
		AtomicInteger totalCareer = new AtomicInteger(0);
		
		
		
		resumeSqs.forEach(
				sq -> {
					totalCareer.addAndGet(resumeCareerMapper.calculateCareerByResSq(sq));
				}
		);
		
		return totalCareer.get();
	}
}
