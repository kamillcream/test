package com.example.demo.domain.mypage.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.domain.mypage.dto.CompanyDTO;
import com.example.demo.domain.mypage.dto.ProjectDTO;
import com.example.demo.domain.mypage.dto.ProjectScrapAddressDTO;
import com.example.demo.domain.mypage.mapper.ProjectScrapMapper;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ProjectScrapRepository {
    private final ProjectScrapMapper mapper;

    public List<Long> findScrappedProjects(Long userSq) {
        return mapper.findScrappedProjectSqsByUserSq(userSq);
    }

    public ProjectDTO findBasic(Long projectSq) {
        return mapper.findProjectBasic(projectSq);
    }

    public CompanyDTO findCompany(Long projectSq) {
        return mapper.findCompanyByProjectSq(projectSq);
    }

    public ProjectScrapAddressDTO findAddress(Long projectSq) {
        return mapper.findAddressByProjectSq(projectSq);
    }

    public String findEducationName(Long codeSq) {
        return mapper.findEducationName(codeSq);
    }

    public String findDeveloperGradeName(Long codeSq) {
        return mapper.findDeveloperGradeName(codeSq);
    }

    public List<String> findSkillTags(Long projectSq) {
        return mapper.findSkillTagsByProjectSq(projectSq);
    }

    public int deleteByUserAndProject(Long userSq, Long projectSq) {
    return mapper.deleteByUserAndProject(userSq, projectSq);
}
}