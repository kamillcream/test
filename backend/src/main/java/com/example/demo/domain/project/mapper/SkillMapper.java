package com.example.demo.domain.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SkillMapper {
	List<String> findAllReqSkillsByProjectSq(@Param("projectSq") Long projectSq);
	List<String> findAllPreferSkillsByProjectSq(@Param("projectSq") Long projectSq);
}
