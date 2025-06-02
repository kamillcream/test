package com.example.demo.domain.mypage.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.mapper.MypageAddressMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/addresses")
@RequiredArgsConstructor
public class AddressController {
	
	private final MypageAddressMapper addressMapper;
	
	@PostMapping
	public ResponseEntity<Long> registerAddress(@RequestBody AddressDTO addressDTO){
	    String sido = addressDTO.getSido();	
		String sigungu = addressDTO.getSigungu();

		   if (sido == null || sido.trim().isEmpty() ||
			        sigungu == null || sigungu.trim().isEmpty()) {
			        throw new IllegalArgumentException("시/도 또는 시군구 정보가 누락되었습니다.");
			    }

		   // 1. 시도 → 상위 지역코드 조회
		    Long parentCode = addressMapper.selectParentAreaCodeBySido(sido.trim());
		    if (parentCode == null) {
		        throw new IllegalArgumentException("[" + sido + "]에 해당하는 지역 코드가 존재하지 않습니다.");
		    }
		    
		    
		    // 2. 시군구 + 상위코드로 지역코드 조회
		    Long areaCodeSq = addressMapper.selectAreaCodeBySigunguAndParent(sigungu.trim(), parentCode);
		    if (areaCodeSq == null) {
		        throw new IllegalArgumentException("[" + sido + " " + sigungu + "]에 해당하는 지역 코드가 존재하지 않습니다.");
		    }

		//그 결과를 addressDTO에 세팅 (숫자 → 변환된 값)
		   addressDTO.setAreaCodeSq(areaCodeSq);
	   	// 이 addressDTO를 주소 테이블에 insert
		   addressMapper.insertAddress(addressDTO);

		//생성된 addressSq 리턴
		return ResponseEntity.ok(addressDTO.getAddressSq());
	}
}
