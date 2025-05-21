package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ParentCodeEnum {

    MEMBER_TYPE(300),
    APPLICATION(500),
    DEVELOPER_GRADE(700),
    CONTRACT_TYPE(900),
    JOB_POSITION(1000),
    EDUCATION(2100);
    private long code;

}