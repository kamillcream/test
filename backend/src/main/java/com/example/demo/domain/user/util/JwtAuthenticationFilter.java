package com.example.demo.domain.user.util;

import java.io.IOException;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    private static final List<String> EXCLUDE_URLS = List.of(
            "/api/login",
            "/api/refresh-token",
            "/api/email/send-code",
            "/api/email/find/send-code",
            "/api/email/verify-code",
            "/api/find-id",
            "/api/reset-password",
            "/api/reset-password/verify",
            "/api/signup",
            "/api/check-id",
            "/api/company/verify",
            // "/api/board",
            // "/api/board/{boardSq}/increment-view",
            // "/api/qna",
            // "/api/qna/{boardSq}/increment-view",
            // "/api/answer/{answerSq}",
            // "/api/answer/{answerSq}/increment-view",
            // "/api/affiliation/guest",
            // "/api/affiliation/{answerSq}/increment-view",
            // "/api/affiliation/address",
            "/api/projects/interviews",
            "/api/projects/forms",
            "/api/projects/*/districts",
            "/api/projects/filters"

    // 여기에 더 추가 가능
    );

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        String token = resolveToken(request);

        // 인증 제외 경로 처리
        if (EXCLUDE_URLS.stream().anyMatch(uri::startsWith)) {
            if (token != null && jwtProvider.validateToken(token)) {
                try {
                    Long userSq = jwtProvider.getUserSqFromToken(token);
                    Long userTypeCd = jwtProvider.getUserTypeCdFromToken(token);

                    JwtAuthenticationToken authentication = new JwtAuthenticationToken(userSq, userTypeCd);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (Exception e) {
                    // 토큰 오류 무시하고 통과 (로그 남기고 싶으면 여기서 처리)
                }
            }

            filterChain.doFilter(request, response);
            return;
        }

        // 인증 필수 경로
        if (token == null || !jwtProvider.validateToken(token)) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        try {
            Long userSq = jwtProvider.getUserSqFromToken(token);
            Long userTypeCd = jwtProvider.getUserTypeCdFromToken(token);

            JwtAuthenticationToken authentication = new JwtAuthenticationToken(userSq, userTypeCd);
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("accessToken".equals(cookie.getName())) {
                    // System.out.println("Found accessToken cookie");
                    return cookie.getValue();
                }
            }
        }
        // System.out.println("No accessToken cookie found");
        return null;
    }

}
