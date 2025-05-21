package com.example.demo.domain.user.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.domain.user.dto.TokenDTO;
import com.example.demo.domain.user.dto.UserDTO;
import com.example.demo.domain.user.dto.response.LoginResponseDTO;
import com.example.demo.domain.user.repository.UserRepository;
import com.example.demo.domain.user.util.JwtProvider;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    @Transactional
    public LoginResultDTO login(String userId, String userPw, Long userTypeCd) {
        UserDTO user = userRepository.findByUserId(userId);

        if (user == null) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        if (!user.getUserTypeCd().equals(userTypeCd)) {
            throw new IllegalArgumentException("회원 유형이 일치하지 않습니다.");
        }

        if (!userPw.equals(user.getUserPw())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        String accessToken = jwtProvider.createAccessToken(user);
        String refreshToken = jwtProvider.createRefreshToken(user);

        userRepository.updateRefreshToken(user.getUserSq(), refreshToken);

        TokenDTO tokenDTO = new TokenDTO(accessToken, refreshToken);
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO(user.getUserNm(), user.getUserTypeCd());

        return new LoginResultDTO(tokenDTO, loginResponseDTO);
    }

    @Transactional
    public TokenDTO refreshToken(String refreshToken) {
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

        return new TokenDTO(newAccessToken, newRefreshToken);
    }

    public class LoginResultDTO {
        private final TokenDTO token;
        private final LoginResponseDTO userInfo;

        public LoginResultDTO(TokenDTO token, LoginResponseDTO userInfo) {
            this.token = token;
            this.userInfo = userInfo;
        }

        public TokenDTO getToken() {
            return token;
        }

        public LoginResponseDTO getUserInfo() {
            return userInfo;
        }
    }

    public LoginResponseDTO getUserInfoByUserSq(Long userSq) {
        return userRepository.getUserInfoByUserSq(userSq);
    }

    public void deleteRefreshTokenByUserSq(Long userSq) {
        userRepository.deleteRefreshTokenByUserSq(userSq);
    }

}
