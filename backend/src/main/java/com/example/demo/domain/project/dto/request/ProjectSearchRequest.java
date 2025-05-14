package com.example.demo.domain.project.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSearchRequest {

    @NotNull(message = "지역 코드(addressCodeSq)는 필수입니다.")
    private Long addressCodeSq;

    @NotNull(message = "페이지 번호(offset)는 필수입니다.")
    @Min(value = 1, message = "페이지 번호는 1 이상이어야 합니다.")
    private Integer offset;

    @NotNull(message = "페이지 크기(size)는 필수입니다.")
    @Min(value = 1, message = "페이지 크기는 1 이상이어야 합니다.")
    private Integer size;

    private String sortBy;                  // 예: "project_start_dt"
    private String sortOrder;               // 예: "asc" 또는 "desc"
    private Long projectDeveloperGradeCd;   // 예: 700
    private String projectIsDeletedYn;      // "Y" or "N"
    private String experience;              // 예: "3년 이상"
    private String education;               // 예: "대졸 이상"
    private String jobRole;                 // 예: "백엔드 개발자"
    private String searchKeyword;           // 예: "AI"
    private String searchType;              // 예: "기술", "프로젝트명" 등
}
