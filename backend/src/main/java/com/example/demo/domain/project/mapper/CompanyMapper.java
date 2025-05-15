package com.example.demo.domain.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CompanyMapper {
	String findCompanyNmByCompanySq(@Param("companySq") Long companySq);
}
