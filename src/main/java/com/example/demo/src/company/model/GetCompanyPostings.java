package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCompanyPostings {
    private long postingId;
    private String title;
    private long money;
    private String deadLine;
}
