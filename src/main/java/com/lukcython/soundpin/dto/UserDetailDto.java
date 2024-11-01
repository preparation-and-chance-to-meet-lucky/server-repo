package com.lukcython.soundpin.dto;

import com.lukcython.soundpin.domain.Users;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDetailDto {
    private String username;
    private String comment;
    private String nickname;

    @Builder
    public UserDetailDto(String username, String comment, String nickname){
        this.username = username;
        this.comment = comment;
        this.nickname = nickname;
    }

    public static UserDetailDto of(Users user){
        return UserDetailDto.builder()
                .username(user.getUsername())
                .comment(user.getComment())
                .nickname(user.getNickname())
                .build();
    }

}
