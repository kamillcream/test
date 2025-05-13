package com.example.demo.domain.user.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.user.dto.request.UserJoinRequestDto;
import com.example.demo.domain.user.dto.response.UserJoinResponseDto;


@Mapper
public interface UserMapper {
    void insertUser(UserJoinRequestDto user);
    int countByEmail(String userEmail);
    UserJoinResponseDto findByEmail(String userEmail);
}
