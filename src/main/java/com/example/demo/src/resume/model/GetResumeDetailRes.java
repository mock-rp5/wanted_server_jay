package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetResumeDetailRes {
    private long resumeId;
    private String resumeTitle;
    private String name;
    private String email;
    private String phone;
    private String introduce;
}
