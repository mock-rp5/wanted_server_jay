package com.example.demo.src.resume.model;

import com.example.demo.src.skill.model.GetSkillsRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetResumeDetailRes {
    private long resumeId;
    private String resumeTitle;
    private String name;
    private String email;
    private String phone;
    private String introduce;
    private List<GetCareerListRes> careerList;
    private List<GetResultListRes> resultList;
    private List<GetEducationListRes> educationList;
    private List<GetResumeSkillListRes> skillList;
    private List<GetEtcListRes> etcList;
    private List<GetLanguageListRes> languageList;
    private List<GetLinkListRes> linkList;
}
