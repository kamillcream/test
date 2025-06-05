package com.example.demo.domain.user.util;

import java.io.IOException;

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

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();

        // 로그인, 리프레시 토큰 요청은 필터 제외
        if (uri.startsWith("/api/login") || uri.startsWith("/api/refresh-token")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = resolveToken(request);
        // System.out.println("토큰값" + token);

        try {
            if (token != null && jwtProvider.validateToken(token)) {
                Long userSq = jwtProvider.getUserSqFromToken(token);
                Long userTypeCd = jwtProvider.getUserTypeCdFromToken(token);

                JwtAuthenticationToken authentication = new JwtAuthenticationToken(
                        userSq, userTypeCd);
                System.out.println("authentication" + authentication);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            System.out.println("JWT validation failed: " + e.getMessage());
            // 여기서 응답을 403으로 막을 수도 있으니 참고
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
