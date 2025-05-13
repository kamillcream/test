package com.example.demo.domain.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.service.EmailVerificationService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailVerificationController {
    @Autowired
    private EmailVerificationService verificationService;

    // 1. 인증 코드 전송 요청
    @PostMapping("/send-code")
    public String sendCode(@RequestParam("email") String email) {
        try {
            verificationService.sendVerificationCode(email);
            return "인증 코드가 이메일로 발송되었습니다.";
        } catch (MessagingException e) {
            return "이메일 발송에 실패했습니다: " + e.getMessage();
        }
    }

    // 2. 인증 코드 검증 요청
    @PostMapping("/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestParam String email,
            @RequestParam String code) {
        boolean result = verificationService.verifyCode(email, code);
        return ResponseEntity.ok(result);
    }
}
