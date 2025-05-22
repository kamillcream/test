package com.example.demo.domain.company.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyMapper {
    String findCompanyNmByCompanySq(@Param("companySq") Long companySq);
    Long findCompanySqByUserSq(@Param("userSq") Long userSq);
}