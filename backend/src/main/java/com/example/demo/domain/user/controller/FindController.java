package com.example.demo.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.user.dto.request.FindIdRequestDTO;
import com.example.demo.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FindController {
    private final UserService userService;

    @PostMapping("/find-id")
    public ApiResponse<String> findUserId(@RequestBody FindIdRequestDTO dto) {
        String userId = userService.findUserId(dto);
        if (userId != null) {
            return ApiResponse.of(HttpStatus.OK, "아이디 조회 성공", userId);
        } else {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "일치하는 사용자를 찾을 수 없습니다.");
        }
    }

}
