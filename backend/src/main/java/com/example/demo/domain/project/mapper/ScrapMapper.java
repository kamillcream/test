package com.example.demo.domain.project.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ScrapMapper {
	public Long findScrapSqByUserSq(@Param("userSq") Long userSq);
}
