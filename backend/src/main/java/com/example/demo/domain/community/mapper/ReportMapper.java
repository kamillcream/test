package com.example.demo.domain.community.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.example.demo.domain.community.entity.*;

import java.util.*;

@Mapper
public interface ReportMapper {
//	신고 조회
    List<Report> findAll();
//	신고 등록
    void insert(Report report);
     
}
