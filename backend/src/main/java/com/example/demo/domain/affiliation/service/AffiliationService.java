package com.example.demo.domain.affiliation.service;


import org.springframework.stereotype.Service;

import com.example.demo.domain.affiliation.mapper.*;
import com.example.demo.domain.community.dto.BoardListDTO;
import com.example.demo.domain.community.dto.SkillTagDTO;
import com.example.demo.domain.community.mapper.CommunityUserMapper;
import com.example.demo.domain.user.dto.UserDTO;
import com.example.demo.config.SecurityConfigDev;
import com.example.demo.domain.affiliation.dto.response.*;
import com.example.demo.domain.affiliation.entity.*;
import com.example.demo.domain.affiliation.dto.request.SearchFilterRequest;
import com.example.demo.domain.mypage.dto.UserInfoDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AffiliationService {

    private final AffiliationMapper affiliationMapper;
    private final CommunityUserMapper communityUserMapper;
    
    // 소속 공고 하나 조회
    @Transactional
    public AffiliationResponse getAffiliaion(Long companySq) {
    	Company company = affiliationMapper.findCompany(companySq);
		Address address = affiliationMapper.findAddress(company.getAddressSq());
		List<String> tags = affiliationMapper.findTags(company.getCompanySq());
		
    	return AffiliationResponse.fromEntity(company, address, tags, null, null);
    }
    
    
    // 소속 공고 전체 리스트 조회
    @Transactional
    public AffiliationListResponse getAllAffiliations(Long userSq, SearchFilterRequest searchFilter) {
    	
    	List<Company> affiliations = affiliationMapper.findAll(searchFilter.getSearchType(), searchFilter.getKeyword(),searchFilter.getSortType(),searchFilter.getAddressCd(),searchFilter.getPage(),searchFilter.getSize(),searchFilter.getOffset());
    	Long totalElements = affiliationMapper.findAllCnt(searchFilter);
    	
    	List<AffiliationResponse> companies = affiliations.stream()
    			.filter(Objects::nonNull)
    			.map(company -> { 
    				Address address = affiliationMapper.findAddress(company.getAddressSq());
    				List<String> tags = affiliationMapper.findTags(company.getCompanySq());
    				Long scrapCnt = affiliationMapper.findScrapCnt(company.getCompanySq());

    		    	Boolean isScrap = false;
    		    	if(userSq != null) {
    		    		Scrap scrap = affiliationMapper.findScrap(userSq, company.getCompanySq());
    		    		if(scrap != null) {
    		    			isScrap = true;
    		    		}    		    		
    		    	}
    				
    				return AffiliationResponse.fromEntity(company, address, tags, scrapCnt, isScrap);
    				
    			}).collect(Collectors.toList());
    	
    	return AffiliationListResponse.builder().page(searchFilter.getPage()).size(searchFilter.getSize()).totalElements(totalElements).viewerSq(userSq).companies(companies).build();
    }
    
    // 소속 공고 스크랩
    @Transactional
    public void updateCompanyRecommend(Long userSq, Long companySq) {
    	
    	if(userSq == null) {
    		throw new IllegalArgumentException("로그인 후 이용해주세요.");
    	}
    	
    	Scrap scrap = affiliationMapper.findScrap(userSq, companySq);
    	System.out.println("스크랩 시도");
    	
    	if(scrap == null) {
    		System.out.println("스크랩 없음" + userSq);
    		System.out.println("스크랩 없음" + companySq);
    		scrap = Scrap.builder().userSq(userSq).companySq(companySq).scrapTypeCd(602L).build();
    		affiliationMapper.insertScrap(scrap);
    		
    	} else {
    		System.out.println("스크랩 있음");
    		affiliationMapper.deleteScrap(scrap.getScrapSq());
    	}
    	
    	return;
    }
    
    // 소속 조회수 증가
    public void addCompanyViewCnt(Long companySq) {
    	affiliationMapper.addViewCnt(companySq);
    	return;
    }
    
    // 소속 신청
    public void addApply(CompanyApplication companyApplication) {
    	if(companyApplication.getUserSq() == null) {
    		throw new IllegalArgumentException("사용자 정보가 없습니다.");
    	}
    	affiliationMapper.insertApplication(companyApplication);
    	return;
    }
    
    // 소속 신청 내용 수정
    public void updateApply(CompanyApplication companyApplication) {
    	CompanyApplication application = affiliationMapper.findApplication(companyApplication.getCompanyApplicationSq());
    	if(application == null) {
    		throw new IllegalArgumentException("등록된 소속 신청 정보가 없습니다.");
    	}
    	
    	if(companyApplication.getUserSq() != application.getUserSq()) {
    		throw new IllegalArgumentException("사용자 정보가 일치하지 않습니다.");
    	}
    	
    	if(companyApplication.getCompanyApplicationGreetingTxt() != null) {
    		application.setCompanyApplicationGreetingTxt(companyApplication.getCompanyApplicationGreetingTxt());
    	}
    	
    	if(companyApplication.getResumeSq() != null) {
    		application.setResumeSq(companyApplication.getResumeSq());
    	}
    	
    	affiliationMapper.updateApplication(application);
    	return;
    }
	
	// 소속 신청 내역 상세 조회
    public CompanyApplication getApply(Long companyApplicationSq) {
    	return affiliationMapper.findApplication(companyApplicationSq);
    }
    
    // 회사별 소속 공고 지원자 내용 전체 조회
    public ApplicantListResponse getAppliesByCompanySq(Long userSq, String searchType, String keyword, String readType, Long page, Long size) {
    	if(page < 1) page = 1L;
    	Long offset = (page - 1L) * size;
    	Long totalElements = affiliationMapper.findApplicantsCnt(userSq, searchType, keyword);
    	Long readElements = affiliationMapper.findApplicantsReadCnt(userSq, searchType, keyword);
    	List<CompanyApplication> applications = affiliationMapper.findApplicants(userSq, searchType, keyword, readType, page, size, offset);
    	List<ApplicantResponse> applicantResponses = applications.stream()
			.filter(Objects::nonNull)
			.map(application -> { 
				List<Career> careers = affiliationMapper.findCareers(application.getResumeSq());
				UserDTO userInfo = communityUserMapper.findById(application.getUserSq());
				List<ResumeSkillTag> skillTags = affiliationMapper.findResumeSkills(application.getResumeSq());
				
				return ApplicantResponse.fromEntity(userInfo, careers, application, skillTags);
				
			}).collect(Collectors.toList());
    	
    	
    	return ApplicantListResponse.builder().page(page).size(size).totalElements(totalElements).readElements(readElements).applicants(applicantResponses).build();
    }
    
    // 합격 또는 불합격 변경
    public void updateApplicationStatus(Long companyApplicationSq, Long companyApplicationStatusCd) {
    	CompanyApplication application = getApply(companyApplicationSq);
    	application.setCompanyApplicationStatusCd(companyApplicationStatusCd);
    	
    	affiliationMapper.updateApplication(application);
    }
    
    // 회원별 소속 신청 내용 전체 조회
    public ApplicationListResponse getAppliesByUserSq(Long userSq, String searchType, String keyword, String readType, Long page, Long size) {
    	if(page < 1) page = 1L;
    	Long offset = (page - 1L) * size;
    	Long totalElements = affiliationMapper.findApplicationByUserSqCnt(userSq, searchType, keyword);
    	Long readElements = affiliationMapper.findApplicationByUserSqReadCnt(userSq, searchType, keyword);
    	List<CompanyApplication> applications = affiliationMapper.findApplicationByUserSq(userSq, searchType, keyword, readType, page, size, offset);
    	List<ApplicationResponse> responses = applications.stream()
    			.filter(Objects::nonNull)
    			.map(application -> {
    				Company company = affiliationMapper.findCompany(application.getCompanySq());
    				String resumeTtl = affiliationMapper.findResumeTtl(application.getResumeSq());
    				Long applicantCnt = affiliationMapper.findApplicantCnt(application.getCompanySq());
    				
    				return ApplicationResponse.fromEntity(company, resumeTtl, application, applicantCnt);
    				
    			}).collect(Collectors.toList());
    	
    	
    	return ApplicationListResponse.builder().applies(responses).size(size).page(page).totalElements(totalElements).readElements(readElements).build();
    }
    
    // 열람 상태 변경
    public void updateApplicationReadAt(Long companyApplicationSq) {
    	CompanyApplication application = getApply(companyApplicationSq);
    	if(application.getCompanyApplicationReadAtDtm() == null) {
    		affiliationMapper.updateReadAt(companyApplicationSq);    		
    	}
    	return;
    }
    
    
    // 소속 신청 취소
    public void deleteApplication(Long companyApplicationSq) {
    	affiliationMapper.deleteApplication(companyApplicationSq);
    	return;
    }
    
    // 주소 리스트 조회
    public List<AreaCd> getAddressList() {
    	return affiliationMapper.findAddressCds();
    }
    
    // 스크랩 리스트 조회
    public AffiliationListResponse getScraps(Long userSq, String searchType, String keyword, Long page, Long size) {
    	if(page < 1) page = 1L;
    	Long offset = (page - 1L) * size;
    	List<Company> companies = affiliationMapper.findScrapAffiliations(userSq, searchType, keyword, page, size, offset);
    	Long totalElements = affiliationMapper.findScrapAffiliationsCnt(userSq, searchType, keyword);
    	
    	List<AffiliationResponse> affiliations = companies.stream()
    			.filter(Objects::nonNull)
    			.map(company -> { 
    				Address address = affiliationMapper.findAddress(company.getAddressSq());
    				List<String> tags = affiliationMapper.findTags(company.getCompanySq());
    				Long memberCnt = affiliationMapper.findAffiliationMemberCnt(company.getCompanySq());
    				
    				return AffiliationResponse.fromEntityScrap(company, address, tags, memberCnt);
    				
    			}).collect(Collectors.toList());
    	
    	return AffiliationListResponse.builder().totalElements(totalElements).page(page).size(size).companies(affiliations).build();
    }
    



}
