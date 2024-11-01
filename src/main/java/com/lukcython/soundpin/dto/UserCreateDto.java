package com.lukcython.soundpin.dto;

import lombok.Getter;

@Getter
public class UserCreateDto {
    private String username;
    private String passwd;

    public UserCreateDto(String username, String passwd){
        this.username = username;
        this.passwd = passwd;
    }
}
