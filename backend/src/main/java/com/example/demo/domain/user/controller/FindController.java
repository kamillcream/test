package com.example.demo.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.user.dto.request.FindIdRequestDTO;
import com.example.demo.domain.user.dto.response.FindIdResponseDTO;
import com.example.demo.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FindController {
    private final UserService userService;

    @PostMapping("/find-id")
    public ApiResponse<FindIdResponseDTO> findUserId(@RequestBody FindIdRequestDTO request) {
        FindIdResponseDTO result = userService.findUserIdByNameAndEmail(request.getName(), request.getEmail());

        if (result != null) {
            return ApiResponse.of(HttpStatus.OK, "아이디 찾기 성공", result);
        } else {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "일치하는 회원 정보를 찾을 수 없습니다.");
        }
    }

}
