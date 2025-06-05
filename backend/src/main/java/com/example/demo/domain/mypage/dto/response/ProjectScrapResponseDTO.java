package com.example.demo.domain.mypage.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.domain.mypage.dto.CompanyDTO;
import com.example.demo.domain.mypage.dto.ProjectScrapAddressDTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectScrapResponseDTO {
    private Long projectSq;
    private String projectTtl;
    private LocalDate recruitStartDt;
    private LocalDate recruitEndDt;
    private int candidateCnt;
    private LocalDateTime createdAt;
    private CompanyDTO company;
    private ProjectScrapAddressDTO address;
    private String requiredEducation;
    private String developerGrade;
    private List<String> skillTags;
    private long dDay;
}
