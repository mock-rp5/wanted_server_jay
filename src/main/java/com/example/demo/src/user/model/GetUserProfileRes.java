package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetUserProfileRes {
    private long userId;
    private String userName;
    private String picture;
    private String email;
    private String phone;
}
