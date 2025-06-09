package com.example.demo.domain.project.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.project.entity.ProjectApplicationEntity;
import com.example.demo.domain.project.vo.ApplicationStatusVo;
import com.example.demo.domain.project.vo.ApplicationSummary;

@Mapper
public interface ProjectApplicationMapper {
	
	public LocalDateTime findInterviewTimeBySq(@Param("interviewSq") Long interviewSq);
	public List<ApplicationSummary> findApplicationSummariesByUserSq(Long userSq);
	public Long findProjectBySq(Long appSq);
	public Long findResumeBySq(Long appSq);
	public Long findCompanyBySq(Long appSq);
	public Long findByProAndUser(@Param("projectSq") Long projectSq, @Param("userSq") Long userSq);
	public Long findByProAndCom(@Param("projectSq") Long projectSq, @Param("companySq") Long companySq);
	
	public ApplicationStatusVo findStatusVoByAppSq(Long applicationSq);
	public List<Long> findAllSqByProjectSq(Long projectSq);
	public String findMmTypStrBySq(Long applicationSq);
	
	
	
	public void updateApplicationStatus(
			@Param("newStatusCd") Long newStatusCd,
			@Param("applicationSq") Long applicationSq);
	
	public void updateInterviewTimeSelected(
			@Param("interviewSq") Long interviewSq);
	
	public void updateApplicationInterviewTimeAndStatus(
			@Param("applicationSq") Long applicationSq,
			@Param("interviewTime") LocalDateTime interviewTime);
	
	
	
}
