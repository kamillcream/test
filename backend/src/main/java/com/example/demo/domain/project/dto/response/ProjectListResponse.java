package com.example.demo.domain.project.dto.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProjectListResponse {
    private Integer page;
    private Integer size;
    private int totalCount;
    private List<ProjectSummary> projects;
}