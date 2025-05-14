package com.example.demo.domain.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.project.entity.ProjectApplicationEntity;

@Repository
public interface ProjectApplicationRepository extends JpaRepository<ProjectApplicationEntity, Long>{
}
