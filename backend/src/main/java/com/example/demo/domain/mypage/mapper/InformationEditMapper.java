package com.example.demo.domain.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.UserInfoDTO;

import org.apache.ibatis.annotations.Param;

@Mapper
public interface InformationEditMapper {
        String selectPasswordByUserSq(Long userSq);

        UserInfoDTO findUserInfoByUserSq(@Param("userSq") Long userSq);

        AddressDTO findAddressByAddressSq(@Param("addressSq") Long addressSq);

        String findCommonCodeNameByGenderCd(@Param("codeSq") Long codeSq);

        String selectCompanyNameByUserSq(@Param("userSq") Long userSq);

        int updateUserInfo(@Param("userSq") Long userSq,
                        @Param("userPw") String userPw,
                        @Param("userEmail") String userEmail,
                        @Param("userPhoneNum") String userPhoneNum);

        int updateUserInfoWithoutPw(@Param("userSq") Long userSq,
                        @Param("userEmail") String userEmail,
                        @Param("userPhoneNum") String userPhoneNum);

        int updateCompanyInfo(@Param("userSq") Long userSq,
                        @Param("userPw") String userPw,
                        @Param("userEmail") String userEmail,
                        @Param("userPhoneNum") String userPhoneNum,
                        @Param("userNm") String userNm);

        int updateCompanyInfoWithoutPw(@Param("userSq") Long userSq,
                        @Param("userEmail") String userEmail,
                        @Param("userPhoneNum") String userPhoneNum,
                        @Param("userNm") String userNm);

        int updateAddress(@Param("userSq") Long userSq,
                        @Param("zonecode") String zonecode,
                        @Param("address") String address,
                        @Param("detailAddress") String detailAddress,
                        @Param("sigungu") String sigungu,
                        @Param("latitude") Double latitude,
                        @Param("longitude") Double longitude);

        boolean existsByEmail(@Param("userEmail") String userEmail);

        boolean existsByPhone(@Param("userPhoneNum") String userPhoneNum);

        Long findUserSqByEmail(@Param("userEmail") String userEmail);

        Long findUserSqByPhone(@Param("userPhoneNum") String userPhoneNum);
}