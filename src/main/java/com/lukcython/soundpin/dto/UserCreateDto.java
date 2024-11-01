package com.lukcython.soundpin.dto;

import lombok.Getter;

@Getter
public class UserCreateDto {
    private String username;
    private String passwd;
    private String comment;
    private String nickname;

    public UserCreateDto(String username, String passwd, String comment, String nickname){
        this.username = username;
        this.passwd = passwd;
        this.comment = comment;
        this.nickname = nickname;
    }
}
