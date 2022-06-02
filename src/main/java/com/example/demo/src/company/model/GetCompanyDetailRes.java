package com.example.demo.src.company.model;

import com.example.demo.src.posting.model.GetPostingListRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCompanyDetailRes {
    private long companyId;
    private String companyName;
    private String companyLogo;
    private List<GetCompanyPicture> companyPictures;
    private String introduce;
    private long salary;
    private String place;
    private List<GetCompanyPostings> postings;
    private List<GetCompanyTags> tags;
}
