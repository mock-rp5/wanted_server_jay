package com.example.demo.src.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCompany {
    private long companyId;
    private String companyName;
    private String companyLogo;
    private String introduce;
    private long salary;
    private String place;
}
