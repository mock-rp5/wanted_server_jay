package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class GetResumeSkillListRes {
    private long resumeId;
    private long resumeSkillId;
    private String skillName;
}
