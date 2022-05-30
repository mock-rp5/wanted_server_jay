package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetResumeBasicRes {
    private long resumeId;
    private String introduce;
    private String resumeTitle;
    private String name;
    private String email;
    private String phone;
}
