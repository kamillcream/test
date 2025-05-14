package com.example.demo.domain.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.mapper.ProjectMapper;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

}
