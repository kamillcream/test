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
import com.example.demo.domain.project.dto.AddressInsertDto;
import com.example.demo.domain.project.dto.request.ContractInsertRequest;
import com.example.demo.domain.project.dto.request.JobInsertRequest;
import com.example.demo.domain.project.dto.request.ProjectApplyRequest;
import com.example.demo.domain.project.dto.request.ProjectCreateRequest;
import com.example.demo.domain.project.dto.request.ProjectSearchRequest;
import com.example.demo.domain.project.dto.request.ScrapInsertRequest;
import com.example.demo.domain.project.dto.request.ScrapRequest;
import com.example.demo.domain.project.dto.request.SkillInsertRequest;
import com.example.demo.domain.project.dto.response.AreaInfoResponse;
import com.example.demo.domain.project.dto.response.ExistProjectVo;
import com.example.demo.domain.project.dto.response.GroupSkillInfoResponse;
import com.example.demo.domain.project.dto.response.ProjectDetailResponse;
import com.example.demo.domain.project.dto.response.ProjectFormDataResponse;
import com.example.demo.domain.project.dto.response.ProjectListResponse;
import com.example.demo.domain.project.dto.response.ProjectSummary;
import com.example.demo.domain.project.dto.response.SingleSkillInfoResponse;
import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectApplicationEntity;
import com.example.demo.domain.project.mapper.AddressMapper;
import com.example.demo.domain.project.mapper.DistrictMapper;
import com.example.demo.domain.project.mapper.ProjectMapper;
import com.example.demo.domain.project.mapper.SkillMapper;
import com.example.demo.domain.project.util.ProjectUtil;
import com.fasterxml.jackson.annotation.JsonTypeInfo.None;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectService {
	private final ProjectMapper projectMapper;
	private final CommonCodeMapper commonCodeMapper;
	private final DistrictMapper districtMapper;
	private final ProjectUtil projectUtil;
	private final SkillMapper skillMapper;
	private final AddressMapper addressMapper;
	
	public void createProject(ProjectCreateRequest request) {
		long devgradeCodeSq = commonCodeMapper.findCommonCodeSqByName(request.devGrade(), ParentCodeEnum.DEVELOPER_GRADE.getCode());
		long educationLvlSq = commonCodeMapper.findCommonCodeSqByName(request.educationLvl(), ParentCodeEnum.EDUCATION.getCode());
		Project project = Project.from(request, registerAddress(request), devgradeCodeSq, educationLvlSq);
		projectMapper.insertProject(project);
		
		registerSubEntities(project, request);
	}
	
	public long registerAddress(ProjectCreateRequest request) {
		AddressInsertDto addressInsertDto = AddressInsertDto.from(request);
		addressMapper.createAddress(addressInsertDto);
		return addressInsertDto.getAddressSq(); 
	}
	
	public void registerSubEntities(Project project, ProjectCreateRequest request) {
		createContracts(project.getProjectSq(), request.workType());
		createJobRoles(project.getProjectSq(), request.recruitJob());
		createReqSkills(project.getProjectSq(), request.usingSkills());
		createPreferSkills(project.getProjectSq(), request.preferSkills());
		createInterviewTimes(project.getProjectSq(), request.interviewTime());
	}
	
	public AreaInfoResponse fetchSubDistrictInfoByProjectSq(Long addressSq) {
		return addressMapper.findAreaInfoBySq(addressSq);
	}
	
	public AreaInfoResponse fetchParentDistrictInfoByCd(Long areaCode) {
		return districtMapper.findParentDisctrictByCodeSq(areaCode);
	}
	
	public String fetchAddressString(Long addressSq) {
	    AreaInfoResponse subDistrict = addressMapper.findAreaInfoBySq(addressSq);
	    AreaInfoResponse parentDistrict = districtMapper.findParentDisctrictByCodeSq(subDistrict.getAreaSq());

	    String parentName = parentDistrict.getAreaName().replace("전체", "").trim();
	    return parentName + " " + subDistrict.getAreaName();
	}
	
	public ProjectListResponse fetchAllProject(ProjectSearchRequest request){
		List<Project> projects = projectMapper.findProjectsBySearch(request);
		int totalCount = projectMapper.countProjectsBySearch(request);
		List<ProjectSummary> responses = new ArrayList<>();
		
		projects.forEach(
			p->{
				responses.add(ProjectSummary.from(p, projectUtil));
			}
		);
		
		return new ProjectListResponse(request.getOffset(), request.getSize(), totalCount, responses);	
	}

	public ProjectDetailResponse fetchProject(Long projectSq){
		Project p = projectMapper.findBySq(projectSq);
		if (p.getProjectIsDeletedYn().equals("Y")) {
			 throw new RuntimeException("이미 삭제된 프로젝트 입니다.");
		}

		projectMapper.updateViewCnt(projectSq);
		
		List<GroupSkillInfoResponse> reqSkills = groupingSkills(projectMapper.findReqSkillsByProjectSq(projectSq));
		List<GroupSkillInfoResponse> preferSkills = groupingSkills(projectMapper.findPreferSkillsByProjectSq(projectSq));
		String projectAddress = fetchAddressString(p.getAddressSq());
		return ProjectDetailResponse.from(p,projectUtil, reqSkills, preferSkills, projectAddress);
	}
	
	@Transactional
	public void updateSkills(Project project, ProjectCreateRequest request) {
		skillMapper.deleteReqSkillsByProjectSq(project.getProjectSq());
		skillMapper.deletePreferSkillsByProjectSq(project.getProjectSq());
		createPreferSkills(project.getProjectSq(), request.preferSkills());
		createReqSkills(project.getProjectSq(), request.usingSkills());
	}
	
	@Transactional
	public void updateContracts(Project project, ProjectCreateRequest request) {
		long projectSq = project.getProjectSq();
		projectMapper.deleteProjectContracts(projectSq);
		projectMapper.insertContracts(projectSq, fillContractInsertRequest(projectSq, request.workType()));
	}
	
	@Transactional
	public void updateJobRoles(Project project, ProjectCreateRequest request) {
		long projectSq = project.getProjectSq();
		projectMapper.deleteProjectJobRoles(projectSq);
		projectMapper.insertJobs(projectSq, fillJobInsertRequest(projectSq, request.recruitJob()));
	}
	
	@Transactional
	public void updateInterviewTimes(Project project, ProjectCreateRequest request) {
		long projectSq = project.getProjectSq();
		projectMapper.deleteProjectInterviewTimes(projectSq);
		createInterviewTimes(projectSq, request.interviewTime());
	}

	public void updateAddress(Project project, ProjectCreateRequest request) {
		projectMapper.deleteProjectAddress(project.getAddressSq()); // 기존 주소 삭제
		long newAddressSq = registerAddress(request);               // 새 주소 insert
		projectMapper.updateAddress(project.getProjectSq(), newAddressSq); // 바로 갱신
	}
	
	@Transactional
	public void updateProject(ProjectCreateRequest request) {
		long devGradeCodeSq = commonCodeMapper.findCommonCodeSqByName(request.devGrade(), ParentCodeEnum.DEVELOPER_GRADE.getCode());
		long educationLvlSq = commonCodeMapper.findCommonCodeSqByName(request.educationLvl(), ParentCodeEnum.EDUCATION.getCode());
		
		Project project = projectMapper.findBySq(request.projectId());
		project.update(request, devGradeCodeSq, educationLvlSq);
		projectMapper.updateProject(project);

		updateSubEntities(project, request);
	}
	
	@Transactional
	public void updateSubEntities(Project project, ProjectCreateRequest request) {
		updateSkills(project, request);
		updateContracts(project, request);
		updateJobRoles(project, request);
		updateInterviewTimes(project, request);
		updateAddress(project, request);
	}

	public void softDeleteProject(long projectSq) {
		projectMapper.softDeleteProject(projectSq);
	}

	public void createProjectApplication(long projectSq, ProjectApplyRequest request) {
		Project project = projectMapper.findBySq(projectSq);
		ProjectApplicationEntity projectApplicationEntity = ProjectApplicationEntity.from(projectSq, projectMapper, request, commonCodeMapper);
		projectMapper.insertProjectApplication(projectApplicationEntity);
		projectMapper.increaseApplication(projectSq);
	}
	
	@Transactional
	public void toggleProjectScrap(long projectSq, ScrapRequest scrapRequest) {
		long userSq = 1;
		boolean hasScrapped = scrapRequest.isHasScrapped();
		if(!hasScrapped) {
			long companySq = projectMapper.findCompanySqFromProjectSq(projectSq);
			long scrapTypeCd = 1;
			ScrapInsertRequest scrapInsertRequest = new ScrapInsertRequest(userSq, companySq, projectSq, scrapTypeCd);
			projectMapper.insertScrap(scrapInsertRequest);
			projectMapper.increaseScrap(projectSq);

		} else {
			projectMapper.deleteProjectScrap(projectSq, userSq);
			projectMapper.decreaseScrap(projectSq);
		}
		
	}
	
	@Transactional
	public void createContracts(Long projectSq, List<String> workTypes) {
		List<ContractInsertRequest> requests = fillContractInsertRequest(projectSq, workTypes);
		projectMapper.insertContracts(projectSq, requests);
	}
	
	
	@Transactional
	public void createJobRoles(Long projectSq, List<String> recruitJobs) {
		List<JobInsertRequest> requests = fillJobInsertRequest(projectSq, recruitJobs);
		projectMapper.insertJobs(projectSq, requests);
	}

	@Transactional
	public void createReqSkills(Long projectSq, List<String> reqSkills) {
		List<SkillInsertRequest> skillInsertRequests = fillSkillInsertRequest(reqSkills);
		projectMapper.insertSkills(projectSq, skillInsertRequests);
	}
	
	@Transactional
	public void createPreferSkills(Long projectSq, List<String> preferSkills) {
		List<SkillInsertRequest> skillInsertRequests = fillSkillInsertRequest(preferSkills);
		projectMapper.insertPreferSkills(projectSq, skillInsertRequests);
	}
	
	@Transactional
	public void createInterviewTimes(Long projectSq, List<LocalDateTime> interviewTimes) {
		projectMapper.insertInterviewTimes(projectSq, interviewTimes);
	}
	
	
	public List<SkillInsertRequest> fillSkillInsertRequest(List<String> skills) {
	    List<SkillInsertRequest> requests = new ArrayList<>();
	    skills.forEach(skillName -> {
	        SkillInsertRequest request = commonCodeMapper.findSkillTagInfoByName(skillName);
	        requests.add(request);
	    });
	    return requests;
	}
	
	public List<ContractInsertRequest> fillContractInsertRequest(Long projectSq, List<String> contracts) {
	    List<ContractInsertRequest> requests = new ArrayList<>();
	    contracts.forEach(contractName -> {
	    	ContractInsertRequest request = new ContractInsertRequest(projectSq, commonCodeMapper.findCommonCodeSqByName(contractName, ParentCodeEnum.CONTRACT_TYPE.getCode()));
	    	requests.add(request);
	    });
	    return requests;
	}
	
	public List<JobInsertRequest> fillJobInsertRequest(Long projectSq, List<String> recruitJobs) {
	    List<JobInsertRequest> requests = new ArrayList<>();
	    recruitJobs.forEach(jobName -> {
	    	JobInsertRequest request = new JobInsertRequest(projectSq, commonCodeMapper.findCommonCodeSqByName(jobName, ParentCodeEnum.JOB_POSITION.getCode())) ;
	    	requests.add(request);
	    });
	    return requests;
	}
	
	public ProjectFormDataResponse fetchProjectFormDatas(long projectSq) {
		List<GroupSkillInfoResponse> skills = groupingSkills(projectMapper.findSkillInfoList());
		if (projectSq != 0L) {
			Project project = projectMapper.findBySq(projectSq);
			AreaInfoResponse areaInfoResponse = fetchSubDistrictInfoByProjectSq(project.getAddressSq());
			ExistProjectVo existProjectVo = ExistProjectVo.from(project, 
					projectUtil, 
					skillMapper.findAllReqSkillsByProjectSq(projectSq), 
					skillMapper.findAllPreferSkillsByProjectSq(projectSq).reversed(),
					fetchParentDistrictInfoByCd(areaInfoResponse.getAreaSq()),
					areaInfoResponse);
			return ProjectFormDataResponse.from(commonCodeMapper, districtMapper, skills, existProjectVo);
		}
		return ProjectFormDataResponse.from(commonCodeMapper, districtMapper, skills);
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
	
	public List<?> fetchFilterInfos(String type){
		switch (type) {
		case "지역": return districtMapper.findAllParentDistrict();
		case "경력": return commonCodeMapper.findCommonCodeSqAndNmByParent(ParentCodeEnum.DEVELOPER_GRADE.getCode());
		case "학력": return commonCodeMapper.findCommonCodeSqAndNmByParent(ParentCodeEnum.EDUCATION.getCode());
		case "직종": return commonCodeMapper.findCommonCodeSqAndNmByParent(ParentCodeEnum.JOB_POSITION.getCode());
		default:
			throw new IllegalArgumentException("Unexpected value: " + type);
		}
	}
}
