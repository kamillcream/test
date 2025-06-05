package com.example.demo.domain.mypage.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.mypage.dto.response.ProjectScrapResponseDTO;
import com.example.demo.domain.mypage.service.ProjectScrapService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class ProjectScrapController {
    private final ProjectScrapService service;

    // @GetMapping("/projectScrap")
    // public ApiResponse<List<ProjectScrapResponseDTO>>
    // getScrapList(@AuthenticationPrincipal Long userSq) {
    // List<ProjectScrapResponseDTO> response = service.getScrappedProjects(userSq);
    // if (response.isEmpty()) {
    // return ApiResponse.error(HttpStatus.NOT_FOUND, "스크랩한 프로젝트가 없습니다.");
    // } else {
    // return ApiResponse.of(HttpStatus.OK, "스크랩한 프로젝트 조회 완료", response);
    // }
    // }

    @GetMapping("/projectScrap")
    public ApiResponse<List<ProjectScrapResponseDTO>> getScrapList(@RequestParam("userSq") Long userSq) {
        List<ProjectScrapResponseDTO> response = service.getScrappedProjects(userSq);
        if (response.isEmpty()) {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "스크랩한 프로젝트가 없습니다.");
        } else {
            return ApiResponse.of(HttpStatus.OK, "스크랩한 프로젝트 조회 완료", response);
        }
    }
}