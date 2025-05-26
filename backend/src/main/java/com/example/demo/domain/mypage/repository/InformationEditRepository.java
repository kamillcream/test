package com.example.demo.domain.mypage.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.UserInfoDTO;
import com.example.demo.domain.mypage.mapper.InformationEditMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InformationEditRepository {

    private final InformationEditMapper mapper;

    public String getEncodedPasswordByUserSq(Long userSq) {
        return mapper.selectPasswordByUserSq(userSq);
    }

    public UserInfoDTO findUser(Long userSq) {
        return mapper.findUserInfoByUserSq(userSq);
    }

    public AddressDTO findAddress(Long addressSq) {
        return mapper.findAddressByAddressSq(addressSq);
    }

    public String findCommonCodeName(Long codeSq) {
        return mapper.findCommonCodeNameByGenderCd(codeSq);
    }
}