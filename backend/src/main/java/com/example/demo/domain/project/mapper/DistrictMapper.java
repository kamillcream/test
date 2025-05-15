package com.example.demo.domain.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.project.dto.response.AreaInfoResponse;

@Mapper
public interface DistrictMapper {
	List<AreaInfoResponse> findAllArea();
	List<AreaInfoResponse> findAllDistrictByParent(@Param("parentCodeSq") Long parentCodeSq);
}
