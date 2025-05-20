package com.example.demo.domain.user.service;

import com.example.demo.domain.user.dto.UserDTO;
import com.example.demo.domain.user.dto.response.LoginResponseDTO;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.user.util.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResponseDTO login(String userId, String userPw) {
        UserDTO user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        // 비밀번호 비교 (암호화 전단계)
        if (!userPw.equals(user.getUserPw())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        // 액세스 토큰 & 리프레시 토큰 생성
        String accessToken = jwtProvider.createAccessToken(user);
        String refreshToken = jwtProvider.createRefreshToken(user);

        // DB에 리프레시 토큰 저장
        userRepository.updateRefreshToken(user.getUserSq(), refreshToken);

        return new LoginResponseDTO(
                accessToken,
                refreshToken,
                user.getUserSq(),
                user.getUserNm(),
                user.getUserEmail());
    }

    @Transactional
    public LoginResponseDTO refreshToken(String refreshToken) {
        if (!jwtProvider.validateToken(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        UserDTO user = userRepository.findByRefreshToken(refreshToken);

        if (user == null) {
            throw new IllegalArgumentException("리프레시 토큰과 일치하는 사용자가 없습니다.");
        }

        // 새로운 액세스 토큰과 리프레시 토큰 발급
        String newAccessToken = jwtProvider.createAccessToken(user);
        String newRefreshToken = jwtProvider.createRefreshToken(user);

        // DB에 리프레시 토큰 갱신
        userRepository.updateRefreshToken(user.getUserSq(), newRefreshToken);

        return new LoginResponseDTO(
                newAccessToken,
                newRefreshToken,
                user.getUserSq(),
                user.getUserNm(),
                user.getUserEmail());
    }
}
