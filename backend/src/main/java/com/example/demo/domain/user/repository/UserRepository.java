package com.example.demo.domain.user.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.user.dto.AddressDTO;
import com.example.demo.domain.user.dto.UserDTO;
import com.example.demo.domain.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public boolean existsByUserId(String userId) {
        return userMapper.existsByUserId(userId);
    }

    public boolean existsByUserEmail(String userEmail) {
        return userMapper.existsByUserEmail(userEmail);
    }

    public boolean existsByUserPhoneNum(String userPhoneNum) {
        return userMapper.existsByUserPhoneNum(userPhoneNum);
    }

    public Long findAreaCodeSqBySigungu(String sigungu) {
        return userMapper.selectAreaCodeSqBySigungu(sigungu);
    }

    public int insertAddress(AddressDTO dto) {
        return userMapper.insertAddress(dto); // addressSq 자동 세팅
    }

    public int insertUser(UserDTO dto) {
        return userMapper.insertUser(dto);
    }

    public UserDTO findByUserId(String userId) {
        return userMapper.findByUserId(userId);
    }

    public void updateRefreshToken(Long userSq, String refreshToken) {
        userMapper.updateRefreshToken(userSq, refreshToken);
    }

    public UserDTO findByRefreshToken(String refreshToken) {
        return userMapper.findByRefreshToken(refreshToken);
    }

}
