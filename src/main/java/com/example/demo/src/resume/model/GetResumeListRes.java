package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class GetResumeListRes {
    private long resumeId;
    private String updatedAt;
    private String resumeStatus;
    private String resumeTitle;
}
