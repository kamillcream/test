package com.example.demo.domain.user.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Data;

@Data
public class RequestSignUpDTO {
    // 사용자 정보
    private String userId;
    private String userEmail;
    private String userPw;
    private String userNm;
    private Long userGenderCd;
    private String userPhoneNum;
    private LocalDate userBirthDt;
    private Long userTypeCd;
    private Long userSignupTypeCd;
    private String userProfileImageUrl;
    private String userAgreedPrivacyPolicyYn;

    // 주소 정보
    private Long zonecode;
    private String address;
    private String detailAddress;
    private String sigungu;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
