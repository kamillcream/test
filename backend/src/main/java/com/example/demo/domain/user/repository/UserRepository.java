package com.example.demo.domain.user.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.user.dto.request.UserJoinRequestDto;
import com.example.demo.domain.user.dto.response.UserJoinResponseDto;
import com.example.demo.domain.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserMapper userMapper;

    public void save(UserJoinRequestDto user) {
        userMapper.insertUser(user);
    }

    public boolean isEmailExists(String email) {
        return userMapper.countByEmail(email) > 0;
    }

    public UserJoinResponseDto findByEmail(String email) {
        return userMapper.findByEmail(email);
    }
}
