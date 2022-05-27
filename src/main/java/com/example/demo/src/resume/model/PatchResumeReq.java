package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchResumeReq {
    private long userId;
    private long resumeId;
    private String introduce;
    private String resumeTitle;
    private String name;
    private String email;
    private String phone;
}
