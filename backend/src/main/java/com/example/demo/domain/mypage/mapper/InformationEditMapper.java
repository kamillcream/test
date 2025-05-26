package com.example.demo.domain.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.UserInfoDTO;

import io.lettuce.core.dynamic.annotation.Param;

@Mapper
public interface InformationEditMapper {
    String selectPasswordByUserSq(Long userSq);

    UserInfoDTO findUserInfoByUserSq(@Param("userSq") Long userSq);

    AddressDTO findAddressByAddressSq(@Param("addressSq") Long addressSq);

    String findCommonCodeNameByGenderCd(@Param("codeSq") Long codeSq);

    String selectCompanyNameByUserSq(@Param("userSq") Long userSq);
}