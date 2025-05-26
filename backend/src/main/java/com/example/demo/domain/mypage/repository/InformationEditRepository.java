package com.example.demo.domain.mypage.repository;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.mypage.mapper.InformationEditMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InformationEditRepository {

    private final InformationEditMapper mapper;

    public String getEncodedPasswordByUserSq(Long userSq) {
        return mapper.selectPasswordByUserSq(userSq);
    }
}