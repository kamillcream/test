package com.example.demo.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.dto.request.SignUpRequestDTO;
import com.example.demo.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SingUpController {

    private final UserService userService;

    // 개인 회원가입
    @PostMapping("/personal/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDTO dto) {
        try {
            userService.signUp(dto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
