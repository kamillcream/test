package com.example.demo.domain.community.controller;


import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import com.example.demo.common.ApiResponse;
import com.example.demo.domain.community.dto.request.*;
import com.example.demo.domain.community.service.ReportService;
import com.example.demo.domain.community.entity.Report;

import lombok.RequiredArgsConstructor;

import javax.lang.model.type.NullType;
import java.util.*;



@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
	
    private final ReportService reportService;
    
//    신고 조회
    @GetMapping
    public ResponseEntity<ApiResponse<List<Report>>> getReports(){
        return ResponseEntity.ok(ApiResponse.of(HttpStatus.OK, "신고 조회 완료", reportService.getReports()));
    }
    
//    신고 등록
    @PostMapping
    public ResponseEntity<ApiResponse<NullType>> createReport(@RequestBody ReportRequest reportRequest){
    	reportService.createReport(reportRequest);
    	return ResponseEntity.ok(ApiResponse.of(HttpStatus.CREATED, "신고 등록이 완료되었습니다.", null));
    	
    }

}