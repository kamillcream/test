package com.example.demo.domain.user.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserJoinRequestDto {
    private String userId;
    private String userEmail;
    private String userPw;
    private String userNm;
    private Long userGenderCd;
    private String userPhoneNum;
    private LocalDate userBirthDt;
    private Long userTypeCd;
    private Long userSignupTypeCd;
    private String userAgreedPrivacyPolicyYn;
}
