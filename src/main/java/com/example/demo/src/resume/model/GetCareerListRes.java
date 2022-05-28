package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCareerListRes {
    private long careerId;
    private String startAt;
    private String endAt;
    private String companyName;
    private String departPosition;
    private String tenure;
    private String now;
}
