package com.example.demo.src.resume.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchResultReq {
    private long resultId;
    private String startAt;
    private String endAt;
    private String result;
}
