package com.example.demo.domain.project.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.project.dto.request.ContractInsertRequest;
import com.example.demo.domain.project.dto.request.JobInsertRequest;
import com.example.demo.domain.project.dto.request.ProjectSearchRequest;
import com.example.demo.domain.project.dto.request.ScrapInsertRequest;
import com.example.demo.domain.project.dto.request.SkillInsertRequest;
import com.example.demo.domain.project.entity.Project;

@Mapper
public interface ProjectMapper {
	List<Project> findProjectsBySearch(ProjectSearchRequest request);
	void insertContracts(@Param("projectSq") Long projectSq, @Param("contractTypes") List<ContractInsertRequest> contractTypes);
	void insertJobs(@Param("projectSq") Long projectSq, @Param("recruitJobs") List<JobInsertRequest> recruitJobs);
	void insertSkills(@Param("projectSq") Long projectSq, @Param("skills") List<SkillInsertRequest> skills);
	void insertPreferSkills(@Param("projectSq") Long projectSq, @Param("preferSkills") List<SkillInsertRequest> preferSkills);
	void insertInterviewTimes(@Param("projectSq") Long projectSq, @Param("interviewTimes") List<LocalDateTime> interviewTimes);
	SkillInsertRequest findSkillTagInfoByName(@Param("name") String name);
	long findCompanySqFromProjectSq(long projectSq);
	void insertScrap(ScrapInsertRequest scrapInsertRequest);
}
