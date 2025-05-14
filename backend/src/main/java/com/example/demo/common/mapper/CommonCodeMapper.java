package com.example.demo.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.project.dto.request.SkillInsertRequest;

@Mapper
public interface CommonCodeMapper {
	Long findCommonCodeSqByName(@Param("name") String name, @Param("parentCodeSq") Long parentCodeSq);
	Long findCommonCodeSqByEngName(@Param("engName") String engName, @Param("parentCodeSq") Long parentCodeSq);
	SkillInsertRequest findSkillTagInfoByName(@Param("name") String name);
	Long findParentSkillTagSq(@Param("skill_tag_sq") Long skill_tag_sq);
}
