package com.example.demo.domain.project.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.project.entity.User;

@Mapper
public interface UserMapper {
     int updateUser(User user);
}
