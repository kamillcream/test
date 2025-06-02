package com.example.demo.domain.company.mapper;

import java.time.LocalDate;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyMapper {
    String findCompanyNmByCompanySq(@Param("companySq") Long companySq);
    String findBizNumByUserSq(@Param("userSq") Long userSq);
    String findBizNumByCompanySq(@Param("companySq") Long companySq);
    
    Long findCompanySqByUserSq(@Param("userSq") Long userSq);
    Long findUserSqByCompanySq(@Param("companySq") Long companySq);
    
    List<Long> findUserSqsByCompanySq(@Param("companySq") Long companySq);
    
    LocalDate findCompanyJoinDt(@Param("userSq") Long userSq);
    LocalDate findCompanyLeaveDt(@Param("userSq") Long userSq);
    
    void updateMemberStatus(@Param("userSq") Long userSq, @Param("memberStatusCd") Long memberStatusCd);
    
}