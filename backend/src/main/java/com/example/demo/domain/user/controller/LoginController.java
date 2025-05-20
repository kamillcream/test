package com.example.demo.domain.user.controller;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.user.dto.request.LoginRequestDTO;
import com.example.demo.domain.user.dto.response.LoginResponseDTO;
import com.example.demo.domain.user.service.LoginService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
            @RequestBody LoginRequestDTO request,
            HttpServletResponse response) {
        LoginResponseDTO loginResponse = loginService.login(request.getUserId(), request.getUserPw());

        // 쿠키 생성 및 설정
        Cookie accessTokenCookie = new Cookie("accessToken", loginResponse.getAccessToken());
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setMaxAge(1800); // 30분

        Cookie refreshTokenCookie = new Cookie("refreshToken", loginResponse.getRefreshToken());
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(60 * 60 * 24 * 14); // 14일

        // 쿠키 응답에 추가
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return ResponseEntity.ok(
                ApiResponse.of(HttpStatus.OK, "로그인 성공", loginResponse));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<LoginResponseDTO>> refreshToken(
            @CookieValue(value = "refreshToken", required = false) String refreshToken,
            HttpServletResponse response) {
        if (refreshToken == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.of(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 없습니다.", null));
        }

        LoginResponseDTO newToken = loginService.refreshToken(refreshToken);

        // 새 액세스 토큰 쿠키로 교체
        Cookie newAccessToken = new Cookie("accessToken", newToken.getAccessToken());
        newAccessToken.setHttpOnly(true);
        newAccessToken.setPath("/");
        newAccessToken.setMaxAge(1800); // 30분

        Cookie newRefreshToken = new Cookie("refreshToken", newToken.getRefreshToken());
        newRefreshToken.setHttpOnly(true);
        newRefreshToken.setPath("/");
        newRefreshToken.setMaxAge(60 * 60 * 24 * 14); // 14일

        response.addCookie(newAccessToken);
        response.addCookie(newRefreshToken);

        return ResponseEntity.ok(
                ApiResponse.of(HttpStatus.OK, "토큰 갱신 성공", newToken));
    }
}
