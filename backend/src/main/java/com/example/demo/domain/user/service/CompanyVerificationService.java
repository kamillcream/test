package com.example.demo.domain.user.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.user.dto.request.CompanyVerificationRequestDTO;

@Service
public class CompanyVerificationService {

    // @Value("${external.business-verify.api-key}")

    private final String apiUrl = "https://api.odcloud.kr/api/nts-businessman/v1/validate?serviceKey=oo7Cptu%2Fmuq0VdvJOvEZ816dEyBChjhrqLIM0HqL2%2BeJeZXKg46MztkspSRsh3HBX%2FlyqoXbNCWB4OydznQ%2Bmg%3D%3D";

    public ApiResponse<Boolean> verifyCompany(CompanyVerificationRequestDTO requestDto) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, String> business = new HashMap<>();
        business.put("b_no", requestDto.getBNo());
        System.out.println("사업자" + requestDto.getBNo());
        business.put("start_dt", requestDto.getStartDt());
        System.out.println("개업일자" + requestDto.getStartDt());
        business.put("p_nm", requestDto.getPNm());
        System.out.println("대표자명" + requestDto.getPNm());
        requestBody.put("businesses", Collections.singletonList(business));
        System.out.println("바디" + requestBody);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        System.out.println(apiUrl);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(apiUrl, entity, Map.class);

            Map<String, Object> body = response.getBody();
            if (body == null || !body.containsKey("data")) {
                return ApiResponse.of(HttpStatus.OK, "사업자 정보 확인 불가", false);
            }

            List<?> dataList = (List<?>) body.get("data");
            boolean isValid = !dataList.isEmpty();

            return ApiResponse.of(HttpStatus.OK, isValid ? "사업자 정보 일치" : "사업자 정보 불일치", isValid);

        } catch (HttpClientErrorException | HttpServerErrorException e) {
            System.out.println("🔴 상태 코드: " + e.getStatusCode());
            System.out.println("🔴 응답 바디: " + e.getResponseBodyAsString());
            return ApiResponse.of(HttpStatus.INTERNAL_SERVER_ERROR, "API 호출 실패", false);
        }
    }
}