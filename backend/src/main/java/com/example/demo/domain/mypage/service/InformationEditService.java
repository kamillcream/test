package com.example.demo.domain.mypage.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.domain.mypage.repository.InformationEditRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InformationEditService {

    private final InformationEditRepository repository;
    private final PasswordEncoder passwordEncoder;

    public boolean checkPassword(Long userSq, String rawPassword) {
        String encodedPw = repository.getEncodedPasswordByUserSq(userSq);
        if (encodedPw == null) {
            throw new RuntimeException("User not found");
        }
        return passwordEncoder.matches(rawPassword, encodedPw);
    }
}
