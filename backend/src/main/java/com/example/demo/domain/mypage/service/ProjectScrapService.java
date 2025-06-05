package com.example.demo.domain.mypage.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.mypage.dto.CompanyDTO;
import com.example.demo.domain.mypage.dto.ProjectDTO;
import com.example.demo.domain.mypage.dto.ProjectScrapAddressDTO;
import com.example.demo.domain.mypage.dto.response.ProjectScrapResponseDTO;
import com.example.demo.domain.mypage.repository.ProjectScrapRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProjectScrapService {
    private final ProjectScrapRepository repository;

    public List<ProjectScrapResponseDTO> getScrappedProjects(Long userSq) {
        List<Long> projectSqs = repository.findScrappedProjects(userSq);

        return projectSqs.stream().map(projectSq -> {
            ProjectDTO basic = repository.findBasic(projectSq);
            CompanyDTO company = repository.findCompany(projectSq);
            ProjectScrapAddressDTO address = repository.findAddress(projectSq);
            String education = repository.findEducationName(basic.getRequiredEducationCd());
            String developer = repository.findDeveloperGradeName(basic.getDeveloperGradeCd());
            List<String> skillTags = repository.findSkillTags(projectSq);

            long dDay = ChronoUnit.DAYS.between(LocalDate.now(), basic.getRecruitEndDt());

            return ProjectScrapResponseDTO.builder()
                    .projectSq(projectSq)
                    .projectTtl(basic.getProjectTtl())
                    .recruitStartDt(basic.getRecruitStartDt())
                    .recruitEndDt(basic.getRecruitEndDt())
                    .candidateCnt(basic.getCandidateCnt())
                    .createdAt(basic.getCreatedAt())
                    .company(company)
                    .address(address)
                    .requiredEducation(education)
                    .developerGrade(developer)
                    .skillTags(skillTags)
                    .dDay(dDay)
                    .build();
        }).collect(Collectors.toList());
    }

    @Transactional
    public boolean deleteProjectScrap(Long userSq, Long projectSq) {
        int affected = repository.deleteByUserAndProject(userSq, projectSq);
        return affected > 0;
    }
}
