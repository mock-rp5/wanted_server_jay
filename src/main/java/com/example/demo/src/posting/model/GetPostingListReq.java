package com.example.demo.src.posting.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class GetPostingListReq {
    private long jobGroupId;
    private String sortType;
    private List<String> location_1;
    private List<String> location_2;
    private List<String> skillTags;
    private long years;
    private List<String> userTags;
    private List<String> jobSelected;
}
