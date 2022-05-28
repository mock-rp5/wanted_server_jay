package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchEducationReq {
    private long educationId;
    private String startAt;
    private String endAt;
    private String now;
    private String name;
    private String major;
    private String info;
}
