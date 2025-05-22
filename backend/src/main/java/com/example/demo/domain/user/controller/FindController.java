package com.example.demo.domain.user.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.user.dto.UserDTO;
import com.example.demo.domain.user.dto.request.FindIdRequestDTO;
import com.example.demo.domain.user.dto.request.ResetPasswordVerifyRequestDTO;
import com.example.demo.domain.user.dto.response.FindIdResponseDTO;
import com.example.demo.domain.user.service.UserService;
import com.example.demo.domain.user.util.JwtProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class FindController {

    private final UserService userService;
    private final JwtProvider jwtProvider;

    @PostMapping("/find-id")
    public ApiResponse<FindIdResponseDTO> findUserId(@RequestBody FindIdRequestDTO request) {
        FindIdResponseDTO result = userService.findUserIdByNameAndEmail(request.getName(), request.getEmail());

        if (result != null) {
            return ApiResponse.of(HttpStatus.OK, "아이디 찾기 성공", result);
        } else {
            return ApiResponse.error(HttpStatus.NOT_FOUND, "일치하는 회원 정보를 찾을 수 없습니다.");
        }
    }

    @PostMapping("/reset-password/verify")
    public ResponseEntity<?> verifyUserForReset(@RequestBody ResetPasswordVerifyRequestDTO dto,
            HttpServletResponse response) {
        UserDTO user = userService.findUserByInfo(dto.getUserId(), dto.getName(), dto.getEmail());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원 정보가 일치하지 않습니다.");
        }

        String resetToken = jwtProvider.createResetToken(user.getUserSq());

        // HttpOnly 쿠키로 설정
        Cookie cookie = new Cookie("RESET_TOKEN", resetToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(300); // 5분
        response.addCookie(cookie);

        if (cookie != null) {
            // 쿠키는 Set-Cookie로 내려주고, 바디는 공백 대신 ApiResponse로 통일
            return ResponseEntity
                    .ok(ApiResponse.of(HttpStatus.OK, "인증 성공", null));
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.error(HttpStatus.BAD_REQUEST, "일치하는 회원 정보를 찾을 수 없습니다."));
        }
    }
}
