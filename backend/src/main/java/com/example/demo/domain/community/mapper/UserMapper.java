package com.example.demo.domain.community.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.community.entity.User;

@Mapper
public interface UserMapper {
     int updateUser(User user);
}
