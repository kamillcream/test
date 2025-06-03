package com.example.demo.domain.mypage.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.mapper.ResumeMapper;
import com.example.demo.domain.mypage.mapper.ResumeSkillMapper;
import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.response.ResumeListResponse;
import com.example.demo.domain.mypage.dto.response.ResumeSkillDataResponse;
import com.example.demo.domain.mypage.dto.response.ResumeSkillPairResponse;
import com.example.demo.domain.mypage.mapper.MypageAddressMapper;
import com.example.demo.domain.mypage.repository.AddressRepository;
import com.example.demo.domain.mypage.dto.response.ResumeRegisterResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeService {
	
	private final ResumeMapper resumeMapper;
	private final ResumeSkillMapper skillMapper;
	private final AddressRepository addressRepository;
	private final MypageAddressMapper addressMapper;


	@Transactional
	public ResumeRegisterResponse registerResume(ResumeRegisterRequest request) {
	    
	    // 유저 정보 강제 지정 (임시)
	    request.setUserSq(18L);
	    
	    // 기본값 설정
	    setDefaultValues(request);

	    // 주소 처리
	    if (request.getAddressSq() == null) {
	        processAddress(request);
	    }

	    // 이력서 등록
	    resumeMapper.insertResume(request);

	    return createResponse(request);
	}

	private void setDefaultValues(ResumeRegisterRequest request) {
	    if (request.getResumeIsRepresentativeYn() == null) {
	        request.setResumeIsRepresentativeYn("N");
	    }
	    if (request.getResumeIsNotificationYn() == null) {
	        request.setResumeIsNotificationYn("N");
	    }
	    if (request.getResumePhotoUrl() == null) {
	        request.setResumePhotoUrl("");
	    }
	}

	private void processAddress(ResumeRegisterRequest request) {
	    // 시군구 기준으로 지역코드 조회
		 System.out.println("request.getSigungu() = " + request.getSigungu());
		    Long areaCodeSq = addressMapper.selectAreaCodeSqBySigungu(request.getSigungu());
		    System.out.println("조회된 areaCodeSq = " + areaCodeSq);

		    if (areaCodeSq == null) {
		        throw new IllegalArgumentException("[" + request.getSigungu() + "]에 해당하는 지역 코드가 존재하지 않습니다.");
		    }

	    // 주소 객체 생성 및 저장
	    AddressDTO addressDTO = AddressDTO.builder()
	        .zonecode(request.getZonecode())
	        .address(request.getAddress())
	        .detailAddress(request.getDetailAddress())
	        .sigungu(request.getSigungu())
	        .latitude(request.getLatitude())
	        .longitude(request.getLongitude())
	        .areaCodeSq(areaCodeSq)
	        .build();

	    addressRepository.insertAddress(addressDTO);
	    request.setAddressSq(addressDTO.getAddressSq());
	}

	private ResumeRegisterResponse createResponse(ResumeRegisterRequest request) {
	    return ResumeRegisterResponse.builder()
	        .resumeSq(request.getResumeSq())
	        .resumeTtl(request.getResumeTtl())
	        .representative("Y".equals(request.getResumeIsRepresentativeYn()))
	        .build();
	}
	
	
	//대표 이력서 설정
	@Transactional
	public void setMainResume(Long resumSq, Long userSq) {
		resumeMapper.updateAllRepresentativeN(userSq);
		resumeMapper.updateRepresentativeY(resumSq);
	}
	

	//이력서 상세조회
	public ResumeRegisterRequest getResumeById(Long resumeSq) {
	    return resumeMapper.selectResumeById(resumeSq);
	}

	//이력서 전체 조회
	public List<ResumeListResponse> getAllResumes(Long userSq) {
	    return resumeMapper.selectAllResumes(userSq);
	}
	//삭제
	public void softDeleteResume(Long resumeSq) {
		resumeMapper.updateDeleteYn(resumeSq);
	}
	//기술 스택
	public List<ResumeSkillDataResponse> getGroupedSkills() {
	    List<ResumeSkillPairResponse> flatList = skillMapper.findSkillInfoList();  // XML 쿼리 실행

	    Map<String, List<String>> grouped = new LinkedHashMap<>();
	    for (ResumeSkillPairResponse item : flatList) {
	        grouped.computeIfAbsent(item.getParentSkillTagNm(), k -> new ArrayList<>())
	               .add(item.getChildSkillTagNm());
	    }

	    return grouped.entrySet().stream()
	        .map(entry -> ResumeSkillDataResponse.builder()
	            .parentSkillTagNm(entry.getKey())
	            .childSkills(entry.getValue())
	            .build())
	        .toList();
	}
}

