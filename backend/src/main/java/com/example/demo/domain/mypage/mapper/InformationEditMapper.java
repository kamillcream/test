package com.example.demo.domain.mypage.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface InformationEditMapper {
    String selectPasswordByUserSq(Long userSq);
}
