package com.example.demo.domain.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AddressMapper {
	String findAddressBySq(@Param("addressSq") Long addressSq);
}
