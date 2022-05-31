package com.example.demo.src.posting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetlikePostingRes {
    private long postingId;
    private long likeId;
    private String likeAt;
    private String companyImg;
    private String companyName;
    private String place;
    private long money;
}
