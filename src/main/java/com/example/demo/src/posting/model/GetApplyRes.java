package com.example.demo.src.posting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetApplyRes {
    private long applyId;
    private long postingId;
    private long companyId;
    private long resumeId;
    private String companyName;
    private String postingTitle;
    private String resumeTitle;
}
