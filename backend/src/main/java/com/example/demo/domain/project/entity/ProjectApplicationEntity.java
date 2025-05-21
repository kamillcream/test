package com.example.demo.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import com.example.demo.common.ParentCodeEnum;
import com.example.demo.common.mapper.CommonCodeMapper;
import com.example.demo.domain.project.dto.request.ProjectApplyRequest;
import com.example.demo.domain.project.entity.enums.ProjectApplicationStatus;
import com.example.demo.domain.project.mapper.ProjectMapper;

@Entity
@Table(name = "TBL_PROJECT_APPLICATION_H")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectApplicationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_application_sq")
    private Long projectApplicationSq;

    @Column(name = "project_sq", nullable = false)
    private Long projectSq;

    @Column(name = "resume_sq", nullable = false)
    private Long resumeSq;

    @Column(name = "company_sq", nullable = false)
    private Long companySq;

    @Column(name = "project_application_status_cd", nullable = false)
    private Long projectApplicationStatusCd;

    @Column(name = "project_application_member_type_cd", nullable = false)
    private Long projectApplicationMemberTypeCd;

    @Column(name = "project_application_created_at_dtm", nullable = false)
    private LocalDateTime projectApplicationCreatedAtDtm;

    @Column(name = "selected_interview_dtm")
    private LocalDateTime selectedInterviewDtm;
    
    @PrePersist
    public void prePersist() {
    	this.projectApplicationCreatedAtDtm = LocalDateTime.now();
    }
    
    public static ProjectApplicationEntity from(long projectSq, ProjectMapper projectMapper,
    		ProjectApplyRequest request, CommonCodeMapper commonCodeMapper) {
    	return ProjectApplicationEntity.builder()
				.projectSq(projectSq)
				.companySq(projectMapper.findCompanySqFromProjectSq(projectSq))
				.resumeSq(request.getResumeSq())
				.projectApplicationStatusCd(commonCodeMapper.findCommonCodeSqByEngName(ProjectApplicationStatus.APPLIED.getCode(), ParentCodeEnum.APPLICATION.getCode()))
				.projectApplicationMemberTypeCd(commonCodeMapper.findCommonCodeSqByEngName(request.getProjectApplicationTyp(), ParentCodeEnum.MEMBER_TYPE.getCode()))
				.build();
    }
    
}