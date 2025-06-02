package com.example.demo.domain.mypage.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.mypage.dto.AddressDTO;
import com.example.demo.domain.mypage.dto.CompanyInfoDTO;
import com.example.demo.domain.mypage.dto.UserInfoDTO;
import com.example.demo.domain.mypage.dto.request.AffiliationInfoUpdateRequestDTO;
import com.example.demo.domain.mypage.dto.request.CompanyUserInfoUpdateRequestDTO;
import com.example.demo.domain.mypage.dto.request.PersonalUserInfoUpdateRequestDTO;
import com.example.demo.domain.mypage.dto.response.AffiliationInfoResponseDTO;
import com.example.demo.domain.mypage.repository.InformationEditRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @Transactional
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

        String sigungu = informationEditRepository.findSigunguByAreaCodeSq(dto.getSigunguCode());

        informationEditRepository.updateAddress(
                userSq,
                dto.getZonecode(),
                dto.getAddress(),
                dto.getDetailAddress(),
                sigungu,
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getSigunguCode());
    }

    @Transactional
    public void updateCompanyInfo(Long userSq, CompanyUserInfoUpdateRequestDTO dto) {
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
        String nameToUpdate = dto.getUserNm();

        if (dto.getUserPw() != null && !dto.getUserPw().isBlank()) {
            String encryptedPw = passwordEncoder.encode(dto.getUserPw());
            informationEditRepository.updateCompany(userSq, encryptedPw, emailToUpdate, phoneToUpdate, nameToUpdate);
        } else {
            informationEditRepository.updateCompanyWithoutPw(userSq, emailToUpdate, phoneToUpdate, nameToUpdate);
        }

        String sigungu = informationEditRepository.findSigunguByAreaCodeSq(dto.getSigunguCode());

        informationEditRepository.updateAddress(
                userSq,
                dto.getZonecode(),
                dto.getAddress(),
                dto.getDetailAddress(),
                sigungu,
                dto.getLatitude(),
                dto.getLongitude(),
                dto.getSigunguCode());
    }

    public AffiliationInfoResponseDTO getAffiliationInfo(Long userSq) {
        CompanyInfoDTO companyInfo = informationEditRepository.getCompanyInfo(userSq);
        if (companyInfo == null) {
            return null;
        }

        UserInfoDTO userInfo = informationEditRepository.getUserInfo(userSq);
        AddressDTO addressInfo = informationEditRepository.getAddressInfo(companyInfo.getAddressSq());
        List<String> tagList = informationEditRepository.getCompanyTags(companyInfo.getCompanySq());

        return AffiliationInfoResponseDTO.of(companyInfo, userInfo, addressInfo, tagList);
    }

    public boolean cancelCompanyRecruiting(Long userSq) {
        int updated = informationEditRepository.updateRecruitingStatusToN(userSq);
        return updated > 0;
    }

    @Transactional
    public void updateAffiliationInfo(Long userSq, AffiliationInfoUpdateRequestDTO dto) {
        try {
            Long companySq = informationEditRepository.findCompanySqByUserSq(userSq);
            if (companySq == null) {
                throw new IllegalArgumentException("해당 사용자의 소속 회사 정보를 찾을 수 없습니다.");
            }

            Long affiliationAddressSq = informationEditRepository.findAffiliationAddressSqByCompanySq(companySq);
            if (affiliationAddressSq == null) {
                throw new IllegalArgumentException("해당 회사의 주소 정보가 존재하지 않습니다.");
            }

            if (dto.getUserPhoneNum() != null && !dto.getUserPhoneNum().isEmpty()) {
                Long phoneOwner = informationEditRepository.findUserSqByPhone(dto.getUserPhoneNum());
                if (phoneOwner != null && !phoneOwner.equals(userSq)) {
                    throw new IllegalArgumentException("이미 사용 중인 휴대폰 번호입니다.");
                }
                informationEditRepository.updateAffiliationPhoneNumByUserSq(userSq, dto.getUserPhoneNum());
            }

            informationEditRepository.updateAffiliationUrlGreetingRecruitingByCompanySq(companySq,
                    dto.getCompanyUrl(),
                    dto.getCompanyGreetingTxt(),
                    dto.getCompanyIsRecruitingYn());

            String sigungu = informationEditRepository.findSigunguByAreaCodeSq(dto.getSigunguCode());
            log.info("조회 시군구명: {}", sigungu);

            informationEditRepository.updateAffiliationAddressByAddressSq(affiliationAddressSq, dto, sigungu);

            informationEditRepository.deleteAffiliationTagsByCompanySq(companySq);
            if (dto.getTagNm() != null && !dto.getTagNm().isEmpty()) {
                dto.getTagNm()
                        .forEach(tag -> informationEditRepository.insertAffiliationTagByCompanySq(companySq, tag));
            }

        } catch (Exception e) {
            log.error("소속 정보 수정 중 예외 발생: {}", e.getMessage(), e);
            throw e; // 트랜잭션 롤백을 위해 다시 던짐
        }
    }

}
