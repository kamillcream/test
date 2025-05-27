package com.example.demo.domain.project.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationStatusList {
	private String applicantType;
	private List<ApplicationStatusResponse> response;
}
