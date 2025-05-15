package com.example.demo.domain.project.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.common.ParentCodeEnum;
import com.example.demo.common.mapper.CommonCodeMapper;
import com.example.demo.domain.project.dto.request.ContractInsertRequest;
import com.example.demo.domain.project.dto.request.JobInsertRequest;
import com.example.demo.domain.project.dto.request.ProjectApplyRequest;
import com.example.demo.domain.project.dto.request.ProjectCreateRequest;
import com.example.demo.domain.project.dto.request.ProjectSearchRequest;
import com.example.demo.domain.project.dto.request.ScrapInsertRequest;
import com.example.demo.domain.project.dto.request.SkillInsertRequest;
import com.example.demo.domain.project.dto.response.AreaInfoResponse;
import com.example.demo.domain.project.dto.response.GroupSkillInfoResponse;
import com.example.demo.domain.project.dto.response.ProjectDetailResponse;
import com.example.demo.domain.project.dto.response.ProjectFormDataResponse;
import com.example.demo.domain.project.dto.response.ProjectListResponse;
import com.example.demo.domain.project.dto.response.ProjectSummary;
import com.example.demo.domain.project.dto.response.SingleSkillInfoResponse;
import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectApplicationEntity;
import com.example.demo.domain.project.entity.enums.ProjectApplicationStatus;
import com.example.demo.domain.project.mapper.DistrictMapper;
import com.example.demo.domain.project.mapper.ProjectMapper;
import com.example.demo.domain.project.repository.ProjectApplicationRepository;
import com.example.demo.domain.project.repository.ProjectRepository;
import com.example.demo.domain.project.util.ProjectUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
	private final ProjectRepository projectRepository;
	private final ProjectApplicationRepository projectApplicationRepository;
	private final ProjectMapper projectMapper;
	private final CommonCodeMapper commonCodeMapper;
	private final DistrictMapper districtMapper;
	private final ProjectUtil projectUtil;
	
	public void createProject(ProjectCreateRequest request) {
		long devgradeCodeSq = commonCodeMapper.findCommonCodeSqByName(request.devGrade(), ParentCodeEnum.DEVELOPER_GRADE.getCode());
		long educationLvlSq = commonCodeMapper.findCommonCodeSqByName(request.educationLvl(), ParentCodeEnum.EDUCATION.getCode());
		Project project = Project.builder()
					.companySq(1L)
					.addressSq(1L)
					.projectTtl(request.projectTitle())
					.projectImageUrl(request.projectImageUrl())
					.projectDeveloperGradeCd(devgradeCodeSq)
					.projectRequiredEducationCd(educationLvlSq)
					.projectSalary(10000L)
					.projectStartDt(request.projectStartDt())
					.projectEndDt(request.projectEndDt())
					.projectRecruitStartDt(request.recruitStartDt())
					.projectRecruitEndDt(request.recruitEndDt())
					.projectDescriptionTxt(request.description())
					.projectPreferenceTxt(request.preference())
					.projectIsNotificationYn(request.isNotification())
					.build();
		projectRepository.save(project);
		createContracts(project.getProjectSq(), request.workType());
		createJobRoles(project.getProjectSq(), request.recruitJob());
		createReqSkills(project.getProjectSq(), request.usingSkills());
		createPreferSkills(project.getProjectSq(), request.preferSkills());
		createInterviewTimes(project.getProjectSq(), request.interviewTime());
	}
	
	public ProjectListResponse fetchAllProject(ProjectSearchRequest request){
		List<Project> projects = projectMapper.findProjectsBySearch(request);
		List<ProjectSummary> responses = new ArrayList<>();
		projects.forEach(
			p->{
				String companyNm = projectUtil.convertCompanySqToName(p.getCompanySq());
				String address = projectUtil.convertAddressSqToName(p.getAddressSq());
				int remainingDay = projectUtil.calcaulateRemainingDay(p.getProjectRecruitEndDt());
				List<String> requiredSkillStrings = projectUtil.fetchReqSkillsByProjectSq(p.getProjectSq());
				String devGradeNm = projectUtil.convertCommonCodeSqToNm(p.getProjectDeveloperGradeCd());
				String eduLvlNm = projectUtil.convertCommonCodeSqToNm(p.getProjectRequiredEducationCd());
				responses.add(ProjectSummary.from(p, companyNm, address, remainingDay, requiredSkillStrings, devGradeNm, eduLvlNm));
			}
		);
		
		return new ProjectListResponse(request.getOffset(), request.getSize(), responses.size(), responses);	
	}
	public ProjectDetailResponse fetchProject(Long projectSq){
		Project p = projectRepository.findById(projectSq).orElseThrow();
		String companyNm = projectUtil.convertCompanySqToName(p.getCompanySq());
		String address = projectUtil.convertAddressSqToName(p.getAddressSq());
		List<String> requiredSkillStrings = projectUtil.fetchReqSkillsByProjectSq(p.getProjectSq());
		List<String> preferredSkillStrings = projectUtil.fetchPreferSkillsByProjectSq(p.getProjectSq());
		List<String> contractTypes = projectUtil.fetchWorkTypesByProjectSq(projectSq);
		List<String> jobs = projectUtil.fetchJobsByProjectSq(projectSq);
		Map<String, LocalDateTime> interviewTimes = projectUtil.fetchInterviewTimesBySq(projectSq);
		String devGradeNm = projectUtil.convertCommonCodeSqToNm(p.getProjectDeveloperGradeCd());
		String eduLvlNm = projectUtil.convertCommonCodeSqToNm(p.getProjectRequiredEducationCd());	
		return ProjectDetailResponse.from(p,
			    interviewTimes,
			    companyNm,
			    address,
			    requiredSkillStrings,
			    preferredSkillStrings,
			    contractTypes,
			    jobs,
			    devGradeNm,
			    eduLvlNm);
	}
	
	@Transactional
	public void updateProject(ProjectCreateRequest request) {
		long devgradeCodeSq = commonCodeMapper.findCommonCodeSqByName(request.devGrade(), ParentCodeEnum.DEVELOPER_GRADE.getCode());
		long educationLvlSq = commonCodeMapper.findCommonCodeSqByName(request.educationLvl(), ParentCodeEnum.EDUCATION.getCode());
		
		Project project = projectRepository.findById(request.projectId()).orElseThrow();
		project.update(request, devgradeCodeSq, educationLvlSq);
	}
	
	@Transactional
	public void softDeleteProject(long projectSq) {
		Project project = projectRepository.findById(projectSq).orElseThrow();
		project.delete();
	}
	
	public ProjectApplicationEntity createProjectApplication(long projectSq, ProjectApplyRequest request) {
		System.out.println(request.getProjectApplicationTyp());
		System.out.println(ParentCodeEnum.MEMBER_TYPE.getCode());
		ProjectApplicationEntity projectApplicationEntity = ProjectApplicationEntity.builder()
				.projectSq(projectSq)
				.companySq(projectMapper.findCompanySqFromProjectSq(projectSq))
				.resumeSq(request.getResumeSq())
				.projectApplicationStatusCd(commonCodeMapper.findCommonCodeSqByEngName(ProjectApplicationStatus.APPLIED.getCode(), ParentCodeEnum.APPLICATION.getCode()))
				.projectApplicationMemberTypeCd(commonCodeMapper.findCommonCodeSqByEngName(request.getProjectApplicationTyp(), ParentCodeEnum.MEMBER_TYPE.getCode()))
				.build();
		return projectApplicationRepository.save(projectApplicationEntity);
	}
	
	@Transactional
	public void createProjectScrap(long projectSq) {
			//CustomUserDetails customUserDetails) {
		//long userSq = customUserDetails.getUserSq();
		long userSq = 1;
		long companySq = projectMapper.findCompanySqFromProjectSq(projectSq);
		long scrapTypeCd = 1;
		ScrapInsertRequest scrapInsertRequest = new ScrapInsertRequest(userSq, companySq, projectSq, scrapTypeCd);
		projectMapper.insertScrap(scrapInsertRequest);
	}
	
	public void createContracts(Long projectSq, List<String> workTypes) {
		List<ContractInsertRequest> requests = fillContractInsertRequest(projectSq, workTypes);
		projectMapper.insertContracts(projectSq, requests);
	}
	
	public void createJobRoles(Long projectSq, List<String> recruitJobs) {
		List<JobInsertRequest> requests = fillJobInsertRequest(projectSq, recruitJobs);
		projectMapper.insertJobs(projectSq, requests);
	}
	
	public void createReqSkills(Long projectSq, List<String> reqSkills) {
		List<SkillInsertRequest> skillInsertRequests = fillSkillInsertRequest(reqSkills);
		projectMapper.insertSkills(projectSq, skillInsertRequests);
	}
	
	public void createPreferSkills(Long projectSq, List<String> preferSkills) {
		List<SkillInsertRequest> skillInsertRequests = fillSkillInsertRequest(preferSkills);
		projectMapper.insertPreferSkills(projectSq, skillInsertRequests);
	}
	
	public void createInterviewTimes(Long projectSq, List<LocalDateTime> interviewTimes) {
		projectMapper.insertInterviewTimes(projectSq, interviewTimes);
	}
	
	
	public List<SkillInsertRequest> fillSkillInsertRequest(List<String> skills) {
	    List<SkillInsertRequest> requests = new ArrayList<>();
	    skills.forEach(skillName -> {
	        SkillInsertRequest request = commonCodeMapper.findSkillTagInfoByName(skillName);
	        if (request != null) {
	            requests.add(request);
	        }
	    });
	    return requests;
	}
	
	public List<ContractInsertRequest> fillContractInsertRequest(Long projectSq, List<String> contracts) {
	    List<ContractInsertRequest> requests = new ArrayList<>();
	    contracts.forEach(contractName -> {
	    	ContractInsertRequest request = new ContractInsertRequest(projectSq, commonCodeMapper.findCommonCodeSqByName(contractName, ParentCodeEnum.CONTRACT_TYPE.getCode()));
	        if (request != null) {
	            requests.add(request);
	        }
	    });
	    return requests;
	}
	
	public List<JobInsertRequest> fillJobInsertRequest(Long projectSq, List<String> recruitJobs) {
	    List<JobInsertRequest> requests = new ArrayList<>();
	    recruitJobs.forEach(jobName -> {
	    	JobInsertRequest request = new JobInsertRequest(projectSq, commonCodeMapper.findCommonCodeSqByName(jobName, ParentCodeEnum.JOB_POSITION.getCode())) ;
	        if (request != null) {
	            requests.add(request);
	        }
	    });
	    return requests;
	}
	
	public ProjectFormDataResponse fetchProjectFormDatas() {
		List<AreaInfoResponse> cities = districtMapper.findAllArea();
		List<String> devGrades = commonCodeMapper.findByParentCode(ParentCodeEnum.DEVELOPER_GRADE.getCode());
		List<String> educationLevels = commonCodeMapper.findByParentCode(ParentCodeEnum.EDUCATION.getCode());
		List<String> workTypes = commonCodeMapper.findByParentCode(ParentCodeEnum.CONTRACT_TYPE.getCode());
		List<String> recruitJobs = commonCodeMapper.findByParentCode(ParentCodeEnum.JOB_POSITION.getCode());
		List<GroupSkillInfoResponse> skills = groupingSkills(projectMapper.findSkillInfoList());
		
		return ProjectFormDataResponse.builder()
				.cities(cities)
				.devGrades(devGrades)
				.educationLevels(educationLevels)
				.workTypes(workTypes)
				.recruitJobs(recruitJobs)
				.skills(skills)
				.build();
	}
	
	public List<GroupSkillInfoResponse> groupingSkills(List<SingleSkillInfoResponse> responses){
		Map<String, List<String>> grouped = responses.stream()
		        .collect(Collectors.groupingBy(
		            SingleSkillInfoResponse::getParentSkillTagNm,
		            Collectors.mapping(SingleSkillInfoResponse::getChildSkillTagNm, Collectors.toList())
		        ));
		
		List<GroupSkillInfoResponse> result = grouped.entrySet().stream()
		        .map(e -> new GroupSkillInfoResponse(e.getKey(), e.getValue()))
		        .collect(Collectors.toList());
		
		return result;	
	}
	
	public List<AreaInfoResponse> fetchDistricts(Long areaCodeSq){
		return districtMapper.findAllDistrictByParent(areaCodeSq);
	}
}
