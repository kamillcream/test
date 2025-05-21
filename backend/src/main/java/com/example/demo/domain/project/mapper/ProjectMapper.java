package com.example.demo.domain.project.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.domain.project.dto.request.ContractInsertRequest;
import com.example.demo.domain.project.dto.request.JobInsertRequest;
import com.example.demo.domain.project.dto.request.ProjectSearchRequest;
import com.example.demo.domain.project.dto.request.ScrapInsertRequest;
import com.example.demo.domain.project.dto.request.SkillInsertRequest;
import com.example.demo.domain.project.dto.response.ProjectFormDataResponse;
import com.example.demo.domain.project.dto.response.SingleSkillInfoResponse;
import com.example.demo.domain.project.entity.Project;

@Mapper
public interface ProjectMapper {
	List<Project> findProjectsBySearch(ProjectSearchRequest request);
	SkillInsertRequest findSkillTagInfoByName(@Param("name") String name);
	long findCompanySqFromProjectSq(long projectSq);
	List<SingleSkillInfoResponse> findSkillInfoList();
	List<String> findWorkTypesByProjectSq(@Param("projectSq") Long projectSq);
	List<String> findJobsByProjectSq(@Param("projectSq") Long projectSq);
	Map<String, LocalDateTime> findInterviewTimeMinMaxBySq(@Param("projectSq") Long projectSq);
	int countProjectsBySearch(ProjectSearchRequest request);
	List<LocalDateTime> findInterviewTimesByProjectSq(@Param("projectSq") Long projectSq);
	
	
	void insertContracts(@Param("projectSq") Long projectSq, @Param("contractTypes") List<ContractInsertRequest> contractTypes);
	void insertJobs(@Param("projectSq") Long projectSq, @Param("recruitJobs") List<JobInsertRequest> recruitJobs);
	void insertSkills(@Param("projectSq") Long projectSq, @Param("skills") List<SkillInsertRequest> skills);
	void insertPreferSkills(@Param("projectSq") Long projectSq, @Param("preferSkills") List<SkillInsertRequest> preferSkills);
	void insertInterviewTimes(@Param("projectSq") Long projectSq, @Param("interviewTimes") List<LocalDateTime> interviewTimes);
	void insertScrap(ScrapInsertRequest scrapInsertRequest);
	
	void deleteProjectScrap(@Param("projectSq") Long projectSq, @Param("userSq") Long userSq);
	void deleteProjectContracts(@Param("projectSq") Long projectSq);
	void deleteProjectJobRoles(@Param("projectSq") Long projectSq);
	void deleteProjectInterviewTimes(@Param("projectSq") Long projectSq);
}
