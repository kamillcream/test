package com.example.demo.domain.mypage.dto.response;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CompanyUserInfoResponseDTO {
    private String userId;
    private String userEmail;
    private String userNm;
    private Date userBirthDt;
    private String userGenderNm;
    private String userPhoneNum;
    private String address;
    private String detailAddress;
    private String companyNm;
}
