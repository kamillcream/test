package com.example.demo.domain.user.util;

import com.example.demo.domain.user.dto.UserDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secretKeyPlain;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration; // 밀리초 단위 (예: 30분 = 1000 * 60 * 30)

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration; // 예: 14일 = 1000L * 60 * 60 * 24 * 14

    private Key secretKey;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyPlain.getBytes());
    }

    public String createAccessToken(UserDTO user) {
        return createToken(user.getUserSq(), user.getUserTypeCd(), accessTokenExpiration);
    }

    public String createRefreshToken(UserDTO user) {
        return createToken(user.getUserSq(), user.getUserTypeCd(), refreshTokenExpiration);
    }

    private String createToken(Long userSq, Long userTypeCd, long expirationTime) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + expirationTime);

        return Jwts.builder()
                .setSubject(String.valueOf(userSq))
                .claim("userTypeCd", userTypeCd)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    public Long getUserSqFromToken(String token) {
        Claims claims = parseClaims(token);
        return Long.valueOf(claims.getSubject());
    }

    public Long getUserTypeCdFromToken(String token) {
        Claims claims = parseClaims(token);
        return claims.get("userTypeCd", Long.class);
    }

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
