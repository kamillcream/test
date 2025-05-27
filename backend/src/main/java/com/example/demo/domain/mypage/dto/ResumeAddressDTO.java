package com.example.demo.domain.mypage.dto;

import lombok.Data;

@Data
public class ResumeAddressDTO {
	    private Long addressSq;
	    private String zonecode;
	    private String address;
	    private String detailAddress;
	    private String sigungu;
	    private Double latitude;
	    private Double longitude;
	    private Long areaCodeSq;

}
