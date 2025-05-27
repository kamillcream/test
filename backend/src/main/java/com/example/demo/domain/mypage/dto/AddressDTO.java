package com.example.demo.domain.mypage.dto;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class AddressDTO {
    private String zonecode;
    private String address;
    private String detailAddress;
    private String sigungu;
    private BigDecimal latitude;
    private BigDecimal longitude;
}
