package com.example.demo.domain.project.dto.request;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectApplyRequest {
    private List<Long> resumeSq;
    private String projectApplicationTyp; //PERSONAL or COMPANY
}