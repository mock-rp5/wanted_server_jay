package com.example.demo.src.posting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPostingListRes {
    private long postingId;
    private String companyPicture;
    private String postingTitle;
    private String companyName;
    private String companyPlace;
    private long money;
}
