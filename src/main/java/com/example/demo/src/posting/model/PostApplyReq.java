package com.example.demo.src.posting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostApplyReq {
    private long postingId;
    private long userId;
    private String name;
    private String email;
    private String phone;
    private long resumeId;
}
