package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchCareerReq {
    private long resumeId;
    private long careerId;
    private String startAt;
    private String endAt;
    private String companyName;
    private String departPosition;
    private String tenure;
    private String now;
}
