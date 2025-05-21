package com.example.demo.domain.user.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.domain.user.service.EmailVerificationService;

import jakarta.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailVerificationController {

    @Autowired
    private EmailVerificationService verificationService;

    // 배포용
    // // 1. 인증 코드 전송 요청
    // @PostMapping("/send-code")
    // public String sendCode(@RequestParam("email") String email) {
    // try {
    // verificationService.sendVerificationCode(email);
    // return "인증 코드가 이메일로 발송되었습니다.";
    // } catch (MessagingException e) {
    // return "이메일 발송에 실패했습니다: " + e.getMessage();
    // }
    // }

    // 테스트용
    // 1. 이메일 인증 코드 전송
    @PostMapping("/send-code")
    public ResponseEntity<Map<String, String>> sendCode(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        try {
            String email = request.get("email");
            String code = verificationService.sendVerificationCode(email);
            response.put("message", "인증 코드가 이메일로 발송되었습니다.");
            response.put("code", code); // 테스트용으로 코드 포함
            return ResponseEntity.ok(response);
        } catch (MessagingException e) {
            response.put("message", "이메일 발송에 실패했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // 2. 인증 코드 검증
    @PostMapping("/verify-code")
    public ResponseEntity<Boolean> verifyCode(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String code = request.get("code");

        boolean result = verificationService.verifyCode(email, code);

        if (result) {
            return ResponseEntity.ok(result); // 인증 성공 시
        } else {
            // 인증 실패 시 400 BAD_REQUEST 상태 코드와 함께 실패 메시지 반환
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(false); // 또는 추가로 실패 메시지 등을 반환할 수 있습니다.
        }
    }

}
