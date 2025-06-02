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
	
	//이력서 등록
	@Transactional
	public ResumeRegisterResponse registerResume(ResumeRegisterRequest request) {
		
		// 유저 정보 기본값 강제
		request.setUserSq(18L);
		System.out.println(" request.getUserSq(): " + request.getUserSq()); // 추후에 지우기


		// null 방지 처리
		if (request.getResumeIsRepresentativeYn() == null) {
		    request.setResumeIsRepresentativeYn("N");
		}
		if (request.getResumeIsNotificationYn() == null) {
		    request.setResumeIsNotificationYn("N");
		}
		if (request.getResumePhotoUrl() == null) {
		    request.setResumePhotoUrl("");
		}
		
		
	    // addressSq가 null인 경우에만 새 주소 insert
	    if (request.getAddressSq() == null) {
	        // 1. 부모 지역코드(sido → parent)
	        Long parentAreaCode = addressMapper.selectParentAreaCodeBySido(request.getSido());

	        // 2. 자식 지역코드(sigungu + parent → 최종)
	        Long areaCodeSq = addressMapper.selectAreaCodeBySigunguAndParent(
	        	    request.getSigungu(),
	        	    parentAreaCode
	        	);

	        // 3. 주소 INSERT
	        AddressDTO addressDTO = new AddressDTO();
	        addressDTO.setZonecode(request.getZonecode());
	        addressDTO.setAddress(request.getAddress());
	        addressDTO.setDetailAddress(request.getDetailAddress());
	        addressDTO.setSigungu(request.getSigungu());
	        addressDTO.setLatitude(request.getLatitude());
	        addressDTO.setLongitude(request.getLongitude());
	        addressDTO.setAreaCodeSq(areaCodeSq);

	        addressRepository.insertAddress(addressDTO);

	        // 4. 이력서 INSERT 시 addressSq 설정
	        request.setAddressSq(addressDTO.getAddressSq());
	    }

	    // 5. 이력서 INSERT
	    resumeMapper.insertResume(request);

	    // 6. 응답 생성
	    ResumeRegisterResponse response = new ResumeRegisterResponse();
	    response.setResumeSq(request.getResumeSq());
	    response.setResumeTtl(request.getResumeTtl());
	    response.setRepresentative("Y".equals(request.getResumeIsRepresentativeYn()));

	    return response;
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

