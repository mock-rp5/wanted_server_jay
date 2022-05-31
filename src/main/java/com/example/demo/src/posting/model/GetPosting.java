package com.example.demo.src.posting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetPosting {
    private long postingId;
    private long companyId;
    private String companyName;
    private String companyPlace;
    private String title;
    private String detail;
    private long recommendMoney;
    private long applyMoney;
    private String placeFull;
}
