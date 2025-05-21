package com.example.demo.domain.user.controller;

import java.time.Duration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.user.dto.TokenDTO;
import com.example.demo.domain.user.dto.request.LoginRequestDTO;
import com.example.demo.domain.user.dto.response.LoginResponseDTO;
import com.example.demo.domain.user.service.LoginService;
import com.example.demo.domain.user.service.LoginService.LoginResultDTO;
import com.example.demo.domain.user.util.JwtAuthenticationToken;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class LoginController {

        private final LoginService loginService;

        @PostMapping("/login")
        public ResponseEntity<ApiResponse<LoginResponseDTO>> login(
                        @RequestBody LoginRequestDTO request,
                        HttpServletResponse httpServletResponse) {
                System.out.println("Login API called with userId: " + request.getUserId());

                LoginResultDTO result = loginService.login(request.getUserId(), request.getUserPw(),
                                request.getUserTypeCd());
                TokenDTO tokens = result.getToken();
                LoginResponseDTO userInfo = result.getUserInfo();

                // 액세스 토큰 쿠키 설정 (항상 30분)
                ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", tokens.getAccessToken())
                                .httpOnly(true)
                                .secure(false)
                                .sameSite("Lax")
                                .path("/")
                                .maxAge(Duration.ofMinutes(30))
                                .build();

                // 리프레시 토큰 쿠키 설정
                ResponseCookie.ResponseCookieBuilder refreshCookieBuilder = ResponseCookie
                                .from("refreshToken", tokens.getRefreshToken())
                                .httpOnly(true)
                                .secure(false)
                                .sameSite("Lax")
                                .path("/");

                if (request.isAutoLogin()) {
                        // 자동 로그인 선택 시 영속 쿠키 (예: 7일 유지)
                        refreshCookieBuilder.maxAge(Duration.ofDays(7));
                } else {
                        // 자동 로그인 미선택 시 세션 쿠키 (maxAge 미설정 = 세션 쿠키)
                        // maxAge 설정 안하면 브라우저 종료 시 삭제됨
                }

                ResponseCookie refreshTokenCookie = refreshCookieBuilder.build();

                httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
                httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

                return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "로그인 성공", userInfo));
        }

        @PostMapping("/refresh-token")
        public ResponseEntity<ApiResponse<Map<String, Object>>> refreshToken(
                        @CookieValue(name = "refreshToken", required = false) String refreshToken,
                        HttpServletResponse httpServletResponse) {

                if (refreshToken == null) {
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                        .body(ApiResponse.of(HttpStatus.UNAUTHORIZED, "리프레시 토큰 없음", null));
                }

                TokenDTO tokens = loginService.refreshToken(refreshToken);

                // 새 토큰 쿠키 갱신
                ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", tokens.getAccessToken())
                                .httpOnly(true)
                                .secure(false)
                                .sameSite("Lax")
                                .path("/")
                                .maxAge(Duration.ofMinutes(30))
                                .build();

                ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", tokens.getRefreshToken())
                                .httpOnly(true)
                                .secure(false)
                                .sameSite("Lax")
                                .path("/")
                                .maxAge(Duration.ofDays(7))
                                .build();

                httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
                httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

                return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "토큰 갱신 성공", null));
        }

        @PostMapping("/me")
        public ResponseEntity<ApiResponse<LoginResponseDTO>> getMyInfo(
                        @AuthenticationPrincipal JwtAuthenticationToken authentication) {

                Long userSq = (Long) authentication.getPrincipal();

                LoginResponseDTO userInfo = loginService.getUserInfoByUserSq(userSq);

                return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "내 정보 조회 성공", userInfo));
        }

        @PostMapping("/logout")
        public ResponseEntity<ApiResponse<Void>> logout(
                        @AuthenticationPrincipal JwtAuthenticationToken authentication,
                        HttpServletResponse httpServletResponse) {

                Long userSq = (Long) authentication.getPrincipal();

                // DB에서 userSq에 해당하는 리프레시 토큰 삭제
                loginService.deleteRefreshTokenByUserSq(userSq);

                // accessToken, refreshToken 쿠키 삭제 (만료시킴)
                ResponseCookie deleteAccessToken = ResponseCookie.from("accessToken", "")
                                .httpOnly(true)
                                .secure(false)
                                .sameSite("Lax")
                                .path("/")
                                .maxAge(0)
                                .build();

                ResponseCookie deleteRefreshToken = ResponseCookie.from("refreshToken", "")
                                .httpOnly(true)
                                .secure(false)
                                .sameSite("Lax")
                                .path("/")
                                .maxAge(0)
                                .build();

                httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, deleteAccessToken.toString());
                httpServletResponse.addHeader(HttpHeaders.SET_COOKIE, deleteRefreshToken.toString());

                return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "로그아웃 성공", null));
        }

}
