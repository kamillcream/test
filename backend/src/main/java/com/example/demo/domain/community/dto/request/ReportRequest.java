package com.example.demo.domain.community.dto.request;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class ReportRequest{
    private Long userSq;
    private Long sq;
    private String reportReasonTxt;
    private Long reportTypeCd;
	
}