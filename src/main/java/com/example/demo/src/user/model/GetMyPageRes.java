package com.example.demo.src.user.model;

import com.example.demo.src.posting.model.GetBookMarkPostingRes;
import com.example.demo.src.posting.model.GetlikePostingRes;
import com.example.demo.src.resume.model.GetLinkListRes;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetMyPageRes {
    private long userId;
    private String userName;
    private String picture;
    private String email;
    private String phone;
    private long applyNum;
    private List<GetBookMarkPostingRes> bookMarks;
    private List<GetlikePostingRes> likes;
}
