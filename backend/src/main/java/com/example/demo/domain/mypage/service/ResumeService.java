package com.example.demo.domain.mypage.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.mypage.dto.request.ResumeRegisterRequest;
import com.example.demo.domain.mypage.mapper.ResumeMapper;
import com.example.demo.domain.mypage.mapper.ResumeSkillMapper;
import com.example.demo.domain.project.dto.response.SingleSkillInfoResponse;
import com.example.demo.domain.user.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.response.ResumeListResponse;
import com.example.demo.domain.mypage.dto.response.ResumeSkillDataResponse;
import com.example.demo.domain.mypage.dto.response.ResumeSkillPairResponse;
import com.example.demo.domain.address.repository.AddressRepository;
import com.example.demo.domain.address.mapper.AddressMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ResumeService {
	
	private final ResumeMapper resumeMapper;
	private final ResumeSkillMapper skillMapper;
	private final AddressRepository addressRepository;
	private final AddressMapper addressMapper;
	
	//이력서 등록
	@Transactional
	public void registerResume(ResumeRegisterRequest request) {
		// 1. 지역 코드 조회
		Long areaCodeSq = addressMapper.selectAreaCodeSqBySigungu(request.getSigungu());

		// 2. 주소 INSERT
		AddressDTO addressDTO = new AddressDTO();
		addressDTO.setZonecode(request.getZonecode());
		addressDTO.setAddress(request.getAddress());
		addressDTO.setDetailAddress(request.getDetailAddress());
		addressDTO.setSigungu(request.getSigungu());
		addressDTO.setLatitude(request.getLatitude());
		addressDTO.setLongitude(request.getLongitude());
		addressDTO.setAreaCodeSq(areaCodeSq);
		addressRepository.insertAddress(addressDTO);

		// 3. 이력서 INSERT (addressSq 참조)
		ResumeDTO resumeDTO = new ResumeDTO();
		resumeDTO.setAddressSq(addressDTO.getAddressSq());
		// ...기타 이력서 필드 세팅...
		resumeRepository.insertResume(resumeDTO);
	}
	
	//이력서 상세조회
	public ResumeRegisterRequest getResumeById(Long resumeSq) {
	    return resumeMapper.selectResumeById(resumeSq);
	}

	//이력서 전체 조회
	public List<ResumeListResponse> getAllResumes() {
	    Long userSq = 1L;
	    return resumeMapper.selectAllResumes();
	}
	//삭제
	public void softDeleteResume(Long resumeSq) {
		resumeMapper.updateDeletedYn(resumeSq);
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

