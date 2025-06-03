package com.example.demo.domain.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.mypage.dto.AddressDTO;

@Mapper
public interface MypageAddressMapper {
	
	Long selectAreaCodeSqBySigungu(String sigungu);
	// 주소 INSERT
    void insertAddress(AddressDTO addressDTO);
}