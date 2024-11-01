package com.lukcython.soundpin.domain;

import com.lukcython.soundpin.dto.UserChangeNicknameDto;
import com.lukcython.soundpin.dto.UserCreateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String passwd;
    private String comment;
    private String nickname;

    @Builder
    public Users(String email, String username, String passwd, String pin){
        this.email = email;
    public User(String username, String passwd, String comment, String nickname){
        this.username = username;
        this.passwd = passwd;
        this.comment = comment;
        this.nickname = nickname;
    }

    public static Users of(UserCreateDto userCreateDto){
        return Users.builder()
                .email(userCreateDto.getEmail())
    public static User of(UserCreateDto userCreateDto){
        return User.builder()
                .username(userCreateDto.getUsername())
                .passwd(userCreateDto.getPasswd())
                .comment(userCreateDto.getComment())
                .nickname(userCreateDto.getNickname())
                .build();
    }

    public void changeNickname(UserChangeNicknameDto userChangeNicknameDto){
        this.nickname = userChangeNicknameDto.getNickname();
    }
}
