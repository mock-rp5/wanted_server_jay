package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetEducationListRes {
    private long educationId;
    private String startAt;
    private String endAt;
    private String now;
    private String name;
    private String major;
    private String info;
}
