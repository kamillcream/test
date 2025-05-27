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

    public void updateUser(Long userSq, String userPw, String userEmail, String userPhoneNum) {
        informationEditMapper.updateUserInfo(userSq, userPw, userEmail, userPhoneNum);
    }

    public void updateUserWithoutPw(Long userSq, String userEmail, String userPhoneNum) {
        informationEditMapper.updateUserInfoWithoutPw(userSq, userEmail, userPhoneNum);
    }

    public void updateAddress(Long userSq, String zonecode, String address, String detailAddress, String sigungu,
            Double lat, Double lon) {
        informationEditMapper.updateAddress(userSq, zonecode, address, detailAddress, sigungu, lat, lon);
    }

    public boolean isEmailDuplicate(String email) {
        return informationEditMapper.existsByEmail(email);
    }

    public boolean isPhoneDuplicate(String phone) {
        return informationEditMapper.existsByPhone(phone);
    }

    public Long findUserSqByEmail(String userEmail) {
        return informationEditMapper.findUserSqByEmail(userEmail);
    }

    public Long findUserSqByPhone(String userPhoneNum) {
        return informationEditMapper.findUserSqByPhone(userPhoneNum);
    }

}