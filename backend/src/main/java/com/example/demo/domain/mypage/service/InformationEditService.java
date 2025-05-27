package com.example.demo.domain.mypage.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.UserInfoDTO;
import com.example.demo.domain.mypage.dto.request.PersonalUserInfoUpdateRequestDTO;
import com.example.demo.domain.mypage.repository.InformationEditRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InformationEditService {

    private final InformationEditRepository informationEditRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean checkPassword(Long userSq, String rawPassword) {
        String encodedPw = informationEditRepository.getEncodedPasswordByUserSq(userSq);
        if (encodedPw == null) {
            throw new RuntimeException("User not found");
        }
        return passwordEncoder.matches(rawPassword, encodedPw);
    }

    public UserInfoDTO getUserInfo(Long userSq) {
        return informationEditRepository.findUser(userSq);
    }

    public AddressDTO getAddress(Long addressSq) {
        return informationEditRepository.findAddress(addressSq);
    }

    public String getGenderName(Long genderCd) {
        return informationEditRepository.findCommonCodeName(genderCd);
    }

    public String getCompanyName(Long userSq) {
        return informationEditRepository.getCompanyNameByUserSq(userSq);
    }

    public void updatePersonalInfo(Long userSq, PersonalUserInfoUpdateRequestDTO dto) {
        // 이메일 중복 검사
        Long emailOwner = informationEditRepository.findUserSqByEmail(dto.getUserEmail());
        if (emailOwner != null && !emailOwner.equals(userSq)) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        // 휴대폰 중복 검사
        Long phoneOwner = informationEditRepository.findUserSqByPhone(dto.getUserPhoneNum());
        if (phoneOwner != null && !phoneOwner.equals(userSq)) {
            throw new IllegalArgumentException("이미 사용 중인 휴대폰 번호입니다.");
        }

        // null일 경우 기존 값과 동일하므로 업데이트에서 제외할 수도 있음
        String emailToUpdate = dto.getUserEmail();
        String phoneToUpdate = dto.getUserPhoneNum();

        if (dto.getUserPw() != null && !dto.getUserPw().isBlank()) {
            String encryptedPw = passwordEncoder.encode(dto.getUserPw());
            informationEditRepository.updateUser(userSq, encryptedPw, emailToUpdate, phoneToUpdate);
        } else {
            informationEditRepository.updateUserWithoutPw(userSq, emailToUpdate, phoneToUpdate);
        }

        informationEditRepository.updateAddress(
                userSq,
                dto.getZonecode(),
                dto.getAddress(),
                dto.getDetailAddress(),
                dto.getSigungu(),
                dto.getLatitude(),
                dto.getLongitude());
    }

}
