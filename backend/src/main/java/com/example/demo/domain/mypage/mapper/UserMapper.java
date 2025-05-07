package com.example.demo.domain.mypage.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.mypage.entity.User;

@Mapper
public interface UserMapper {
     int updateUser(User user);
}
