package com.example.demo.domain.mypage.dto.request;

import lombok.*;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

@Data

public class ResumeRegisterRequest {
    private Long userSq;
    private Long addressSq;
    private String resumeTtl;
    private String resumePhotoUrl;
    private String resumeNm;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate resumeBirthDt;
    private String resumePhoneNum;
    private String resumeEmail;
    private String resumeGreetingTxt;
    private String resumeIsNotificationYn;
    private String resumeIsRepresentativeYn;
}
