package com.example.demo.domain.project.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

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
    
}