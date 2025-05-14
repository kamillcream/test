package com.example.demo.domain.project.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.project.dto.request.ProjectApplyRequest;
import com.example.demo.domain.project.dto.request.ProjectCreateRequest;
import com.example.demo.domain.project.dto.request.ProjectSearchRequest;
import com.example.demo.domain.project.entity.Project;
import com.example.demo.domain.project.entity.ProjectApplicationEntity;
import com.example.demo.domain.project.service.ProjectService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
	private final ProjectService projectService;
	
	@PostMapping
	public ResponseEntity<Project> postProject(@RequestBody ProjectCreateRequest request){
		return ResponseEntity.ok(projectService.createProject(request));
	}
	
	@GetMapping
	public ResponseEntity<List<Project>> getProjectList(@ModelAttribute ProjectSearchRequest request){
		return ResponseEntity.ok(projectService.fetchAllProject(request));
	}
	
	@PatchMapping
	public ResponseEntity<Project> patchProject(@RequestBody ProjectCreateRequest request){
		return ResponseEntity.ok(projectService.updateProject(request));
	}
	
	@DeleteMapping("/{projectSq}")
	public ResponseEntity<Project> deleteProject(@PathVariable("projectSq") Long projectSq){
		return ResponseEntity.ok(projectService.softDeleteProject(projectSq));
	}
	
	@PostMapping("/{projectSq}/applications")
	public ResponseEntity<ProjectApplicationEntity> applyProject(@PathVariable("projectSq") Long projectSq, @RequestBody ProjectApplyRequest applyRequest){
		return ResponseEntity.ok(projectService.createProjectApplication(projectSq, applyRequest));
	}
	
	@PostMapping("/{projectSq}/scrap")
	public void applyProject(@PathVariable("projectSq") Long projectSq){
		projectService.createProjectScrap(projectSq);
	}
	
	@GetMapping("/{projectSq}/details")
	public ResponseEntity<Project> getProjectDetails(@PathVariable("projectSq") Long projectSq){
		return ResponseEntity.ok(projectService.fetchProject(projectSq));
	}
	
	
	
	
}
