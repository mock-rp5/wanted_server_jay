package com.example.demo.src.posting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPostingRes {
    private long postingId;
    private long companyId;
    private String companyName;
    private String companyPlace;
    private List<String> picture;
    private List<String> tag;
    private String title;
    private String detail;
    private long recommendMoney;
    private long applyMoney;
    private String placeFull;
    private int like;
    private int bookMark;
}
