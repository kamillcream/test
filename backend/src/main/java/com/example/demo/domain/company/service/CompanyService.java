package com.example.demo.domain.company.service;

import org.springframework.stereotype.Service;

import com.example.demo.common.ParentCodeEnum;
import com.example.demo.common.mapper.CommonCodeMapper;
import com.example.demo.domain.company.mapper.CompanyMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CompanyService {
	private final CompanyMapper companyMapper;
	private final CommonCodeMapper commonCodeMapper;
	
	public Long fetchCompanySq(Long userSq, Long userTypeCd) {
		if (userTypeCd.equals(commonCodeMapper.findCommonCodeSqByEngName("COMPANY", ParentCodeEnum.MEMBER_TYPE.getCode()))) {
		    return companyMapper.findCompanySqByUserSq(userSq);
		} else {
		    throw new RuntimeException("접근 권한이 없습니다.");
		}
	}
}
