package com.lukcython.soundpin.dto;

import lombok.Getter;

@Getter
public class UserCreateDto {
    private String username;
    private String email;
    private String pin;
    private String passwd;
}
