package com.example.demo.domain.mypage.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.UserInfoDTO;
import com.example.demo.domain.mypage.mapper.InformationEditMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InformationEditRepository {

    private final InformationEditMapper informationEditMapper;

    public String getEncodedPasswordByUserSq(Long userSq) {
        return informationEditMapper.selectPasswordByUserSq(userSq);
    }

    public UserInfoDTO findUser(Long userSq) {
        return informationEditMapper.findUserInfoByUserSq(userSq);
    }

    public AddressDTO findAddress(Long addressSq) {
        return informationEditMapper.findAddressByAddressSq(addressSq);
    }

    public String findCommonCodeName(Long codeSq) {
        return informationEditMapper.findCommonCodeNameByGenderCd(codeSq);
    }

    public String getCompanyNameByUserSq(Long userSq) {
        return informationEditMapper.selectCompanyNameByUserSq(userSq);
    }
}