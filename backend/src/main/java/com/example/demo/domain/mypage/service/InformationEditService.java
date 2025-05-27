package com.example.demo.domain.mypage.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.UserInfoDTO;
import com.example.demo.domain.mypage.dto.response.PersonalUserInfoResponseDTO;
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
}
