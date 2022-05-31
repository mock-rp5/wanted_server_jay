package com.example.demo.src.posting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetBookMarkPostingRes {
    private long postingId;
    private long bookMarkId;
    private String bookMarkAt;
    private String companyImg;
    private String companyName;
    private String place;
    private long money;
}
