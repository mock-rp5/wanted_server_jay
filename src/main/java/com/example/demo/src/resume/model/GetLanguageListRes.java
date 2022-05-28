package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetLanguageListRes {
    private long languageId;
    private String language;
    private String testName;
    private String score;
    private String testAt;
}
