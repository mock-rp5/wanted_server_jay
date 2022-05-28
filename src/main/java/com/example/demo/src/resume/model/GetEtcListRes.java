package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetEtcListRes {
    private long etcId;
    private String date;
    private String name;
    private String detail;
}
