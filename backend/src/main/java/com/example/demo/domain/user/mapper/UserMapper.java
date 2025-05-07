package com.example.demo.domain.user.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.user.entity.User;

@Mapper
public interface UserMapper {
     int updateUser(User user);
}
