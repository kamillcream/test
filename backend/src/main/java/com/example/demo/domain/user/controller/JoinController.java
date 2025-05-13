package com.example.demo.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.dto.request.UserJoinRequestDto;
import com.example.demo.domain.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class JoinController {

    private final UserService userService;

    @PostMapping("/personalJoin")
    public ResponseEntity<String> join(@RequestBody UserJoinRequestDto requestDto) {
        try {
            userService.join(requestDto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
