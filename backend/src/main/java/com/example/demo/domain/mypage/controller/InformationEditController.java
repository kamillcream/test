package com.example.demo.domain.mypage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.UserInfoDTO;
import com.example.demo.domain.mypage.dto.request.PasswordCheckRequestDTO;
import com.example.demo.domain.mypage.dto.request.PersonalUserInfoUpdateRequestDTO;
import com.example.demo.domain.mypage.dto.response.CompanyUserInfoResponseDTO;
import com.example.demo.domain.mypage.dto.response.PersonalUserInfoResponseDTO;
import com.example.demo.domain.mypage.service.InformationEditService;

@RestController
@RequestMapping("/mypage/edit")
public class InformationEditController {

    private final InformationEditService informationEditService;

    public InformationEditController(InformationEditService informationEditService) {
        this.informationEditService = informationEditService;
    }

    @PostMapping("/check-password")
    public ApiResponse<Boolean> checkPassword(
            @AuthenticationPrincipal Long userSq,
            @RequestBody PasswordCheckRequestDTO dto) {

        boolean matches = informationEditService.checkPassword(userSq, dto.getCurrentPassword());

        if (matches) {
            return ApiResponse.of(HttpStatus.OK, "비밀번호 일치", true);
        } else {
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
    }

    @GetMapping("/info")
    public ApiResponse<?> getUserInfo(@AuthenticationPrincipal Long userSq) {
        UserInfoDTO user = informationEditService.getUserInfo(userSq);
        if (user == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다.");
        }

        if (user.getUserTypeCd().equals(301L)) {
            // 개인 회원
            AddressDTO address = user.getAddressSq() != null
                    ? informationEditService.getAddress(user.getAddressSq())
                    : null;

            String genderName = user.getUserGenderCd() != null
                    ? informationEditService.getGenderName(user.getUserGenderCd())
                    : null;

            PersonalUserInfoResponseDTO response = PersonalUserInfoResponseDTO.builder()
                    .userId(user.getUserId())
                    .userEmail(user.getUserEmail())
                    .userNm(user.getUserNm())
                    .userBirthDt(user.getUserBirthDt())
                    .userGenderNm(genderName != null ? genderName : null)
                    .userPhoneNum(user.getUserPhoneNum())
                    .userProfileImageUrl(user.getUserProfileImageUrl())
                    .zonecode(address != null ? address.getZonecode() : null)
                    .address(address != null ? address.getAddress() : null)
                    .detailAddress(address != null ? address.getDetailAddress() : null)
                    .sigungu(address != null ? address.getSigungu() : null)
                    .latitude(address != null ? address.getLatitude() : null)
                    .longitude(address != null ? address.getLongitude() : null)
                    .build();

            return ApiResponse.of(HttpStatus.OK, "개인정보 조회 완료", response);
        } else if (user.getUserTypeCd().equals(302L)) {
            AddressDTO address = user.getAddressSq() != null
                    ? informationEditService.getAddress(user.getAddressSq())
                    : null;

            String genderName = user.getUserGenderCd() != null
                    ? informationEditService.getGenderName(user.getUserGenderCd())
                    : null;

            String companyName = informationEditService.getCompanyName(userSq);

            CompanyUserInfoResponseDTO response = CompanyUserInfoResponseDTO.builder()
                    .userId(user.getUserId())
                    .userEmail(user.getUserEmail())
                    .userNm(user.getUserNm())
                    .userBirthDt(user.getUserBirthDt())
                    .userGenderNm(genderName != null ? genderName : null)
                    .userPhoneNum(user.getUserPhoneNum())
                    .zonecode(address != null ? address.getZonecode() : null)
                    .address(address != null ? address.getAddress() : null)
                    .detailAddress(address != null ? address.getDetailAddress() : null)
                    .sigungu(address != null ? address.getSigungu() : null)
                    .latitude(address != null ? address.getLatitude() : null)
                    .longitude(address != null ? address.getLongitude() : null)
                    .companyNm(companyName)
                    .build();

            return ApiResponse.of(HttpStatus.OK, "기업회원 정보 조회 완료", response);
        } else {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "유효하지 않은 회원입니다.");
        }
    }

    @PostMapping("/update")
    public ApiResponse<?> updateInformation(@AuthenticationPrincipal Long userSq,
            @RequestBody PersonalUserInfoUpdateRequestDTO dto) {
        UserInfoDTO user = informationEditService.getUserInfo(userSq);
        if (user == null) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다.");
        }

        if (user.getUserTypeCd().equals(301L)) {
            // 개인 회원
            informationEditService.updatePersonalInfo(userSq, dto);

            return ApiResponse.of(HttpStatus.OK, "개인회원 정보 업데이트 완료", null);
        } else if (user.getUserTypeCd().equals(302L)) {

            return ApiResponse.of(HttpStatus.OK, "기업회원 정보 업데이트 예정", null);
        } else {
            return ApiResponse.error(HttpStatus.BAD_REQUEST, "유효하지 않은 회원입니다.");
        }
    }
}
