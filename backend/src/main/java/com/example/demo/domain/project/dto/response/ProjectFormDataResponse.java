package com.example.demo.domain.project.dto.response;

import java.util.List;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectFormDataResponse {
	private List<AreaInfoResponse> cities; //  상위 행정구역만
	private List<String> devGrades;
	private List<String> educationLevels;
	private List<String> workTypes;
	private List<String> recruitJobs;
	private List<GroupSkillInfoResponse> skills;
}
