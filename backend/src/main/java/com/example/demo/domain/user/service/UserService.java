package com.example.demo.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.user.dto.AddressDTO;
import com.example.demo.domain.user.dto.CompanyProfileDTO;
import com.example.demo.domain.user.dto.UserDTO;
import com.example.demo.domain.user.dto.request.SignUpRequestDTO;
import com.example.demo.domain.user.dto.response.LoginResponseDTO;
import com.example.demo.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public boolean isUserIdExists(String userId) {
        return userRepository.existsByUserId(userId);
    }

    @Transactional
    public void signUp(SignUpRequestDTO requestDto) {

        // 중복 검사
        if (userRepository.existsByUserId(requestDto.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (userRepository.existsByUserEmail(requestDto.getUserEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        if (userRepository.existsByUserPhoneNum(requestDto.getUserPhoneNum())) {
            throw new IllegalArgumentException("이미 사용 중인 휴대폰 번호입니다.");
        }

        // 2. 지역 코드 조회
        Long areaCodeSq = userRepository.findAreaCodeSqBySigungu(requestDto.getSigungu());

        // 3. 주소 INSERT
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setZonecode(requestDto.getZonecode());
        addressDTO.setAddress(requestDto.getAddress());
        addressDTO.setDetailAddress(requestDto.getDetailAddress());
        addressDTO.setSigungu(requestDto.getSigungu());
        addressDTO.setLatitude(requestDto.getLatitude());
        addressDTO.setLongitude(requestDto.getLongitude());
        addressDTO.setAreaCodeSq(areaCodeSq);
        userRepository.insertAddress(addressDTO);

        // 4. 사용자 INSERT
        UserDTO userDTO = new UserDTO();
        userDTO.setAddressSq(addressDTO.getAddressSq());
        userDTO.setUserId(requestDto.getUserId());
        userDTO.setUserEmail(requestDto.getUserEmail());
        userDTO.setUserPw(requestDto.getUserPw());
        userDTO.setUserNm(requestDto.getUserNm());
        userDTO.setUserGenderCd(requestDto.getUserGenderCd());
        userDTO.setUserPhoneNum(requestDto.getUserPhoneNum());
        userDTO.setUserBirthDt(requestDto.getUserBirthDt());
        userDTO.setUserTypeCd(requestDto.getUserTypeCd());
        userDTO.setUserSignupTypeCd(requestDto.getUserSignupTypeCd());

        userRepository.insertUser(userDTO);

        // 5. 기업회원인 경우 기업정보 INSERT
        // 예: userTypeCd가 2이면 기업회원이라 가정
        if (requestDto.getUserTypeCd() != null && requestDto.getUserTypeCd() == 302L) {
            CompanyProfileDTO companyProfileDTO = new CompanyProfileDTO();
            companyProfileDTO.setUserSq(userDTO.getUserSq()); // user insert 후 자동 생성된 PK 필요
            companyProfileDTO.setAddressSq(addressDTO.getAddressSq());
            companyProfileDTO.setCompanyNm(requestDto.getCompanyNm());
            companyProfileDTO.setCompanyCeoNm(requestDto.getCompanyCeoNm());
            companyProfileDTO.setCompanyOpenDt(requestDto.getCompanyOpenDt());
            companyProfileDTO.setCompanyBizNum(requestDto.getCompanyBizNum());

            userRepository.insertCompanyProfile(companyProfileDTO);
        }
    }

    public LoginResponseDTO getUserInfoByUserSq(Long userSq) {
        return userRepository.getUserInfoByUserSq(userSq);
    }

}
