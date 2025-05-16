package com.example.demo.domain.user.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.user.dto.request.LoginRequestDTO;
import com.example.demo.domain.user.dto.response.LoginResponseDTO;
import com.example.demo.domain.user.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(@RequestBody LoginRequestDTO request) {
        LoginResponseDTO response = loginService.login(request.getUserEmail(), request.getUserPw());
        return ResponseEntity.ok(
                ApiResponse.of(HttpStatus.OK, "로그인 성공", response));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> refreshToken(@RequestBody String refreshToken) {
        LoginResponseDTO response = loginService.refreshToken(refreshToken);
        return ResponseEntity.ok(
                ApiResponse.of(HttpStatus.OK, "토큰 갱신 성공", response));
    }
}
