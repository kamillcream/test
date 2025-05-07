package com.example.demo.domain.company.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.company.entity.User;

@Mapper
public interface UserMapper {
     int updateUser(User user);
}
