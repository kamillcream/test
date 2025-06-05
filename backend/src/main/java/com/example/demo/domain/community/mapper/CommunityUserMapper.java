package com.example.demo.domain.community.mapper;


import org.apache.ibatis.annotations.Mapper;
import com.example.demo.domain.user.dto.UserDTO;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommunityUserMapper {
     UserDTO findById(@Param("userSq") Long userSq);
     
}