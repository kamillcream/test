package com.example.demo.domain.project.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.example.demo.common.mapper.CommonCodeMapper;
import com.example.demo.domain.company.mapper.CompanyMapper;
import com.example.demo.domain.project.dto.response.InterviewTimeSlot;
import com.example.demo.domain.project.mapper.AddressMapper;
import com.example.demo.domain.project.mapper.ProjectMapper;
import com.example.demo.domain.project.mapper.SkillMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProjectUtil {
	private final AddressMapper addressMapper;
	private final CompanyMapper companyMapper;
	private final SkillMapper skillMapper;
	private final CommonCodeMapper commonCodeMapper;
	private final ProjectMapper projectMapper;
	
	
	public String convertAddressSqToName(Long addressSq) {
		return addressMapper.findAddressBySq(addressSq);
	}
	
	public String convertCompanySqToName(Long companySq) {
		return companyMapper.findCompanyNmByCompanySq(companySq);
	}
	
	
	public List<String> fetchReqSkillsByProjectSq(Long projectSq) {
		return skillMapper.findAllReqSkillsByProjectSq(projectSq);
	}
	
	public List<String> fetchPreferSkillsByProjectSq(Long projectSq) {
		return skillMapper.findAllPreferSkillsByProjectSq(projectSq);
	}
	
	public List<String> fetchWorkTypesByProjectSq(Long projectSq) {
		return projectMapper.findWorkTypesByProjectSq(projectSq);
	}
	
	public List<String> fetchJobsByProjectSq(Long projectSq) {
		return projectMapper.findJobsByProjectSq(projectSq);
	}
	
	public Map<String, LocalDateTime> fetchInterviewTimeMinMaxBySq(Long projectSq){
		return projectMapper.findInterviewTimeMinMaxBySq(projectSq);
	}
	
	public String convertCommonCodeSqToNm(Long codeSq) {
		return commonCodeMapper.findCommonCodeNmBySq(codeSq);
	}
	
	public Map<String, List<String>> fetchAndConvertTimeSlots(Long projectSq){
		List<LocalDateTime> rawInterviewTimes = projectMapper.findAllInterviewTimesByProjectSq(projectSq);
		Map<String, List<String>> timeSlots = new HashMap<>();
		
		rawInterviewTimes.forEach(
				t -> {
					String date = t.toLocalDate().toString();
					String time = t.toLocalTime().toString();
					timeSlots.computeIfAbsent(date, k -> new ArrayList<>()).add(time);
				}
		);
		return timeSlots;
	}
}
