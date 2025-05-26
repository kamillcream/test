package com.example.demo.domain.mypage.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.mypage.dto.request.PasswordCheckRequestDTO;
import com.example.demo.domain.mypage.service.InformationEditService;

@RestController
@RequestMapping("/mypage")
public class InformationEditController {

    private final InformationEditService informationEditService;

    public InformationEditController(InformationEditService informationEditService) {
        this.informationEditService = informationEditService;
    }

    @PostMapping("/check-password")
    public ApiResponse<Boolean> checkPassword(
            @AuthenticationPrincipal Long userSq,
            @RequestBody PasswordCheckRequestDTO dto) {

        boolean matches = informationEditService.checkPassword(userSq, dto.getCurrentPassword());

        if (matches) {
            return ApiResponse.of(HttpStatus.OK, "비밀번호 일치", true);
        } else {
            return ApiResponse.error(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다.");
        }
    }
}
