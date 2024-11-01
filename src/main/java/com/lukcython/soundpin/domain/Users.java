package com.lukcython.soundpin.domain;

import com.lukcython.soundpin.dto.UserCreateDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String username;
    private String passwd;
    private String pin;

    @Builder
    public Users(String email, String username, String passwd, String pin){
        this.email = email;
        this.username = username;
        this.passwd = passwd;
        this.pin = pin;
    }

    public static Users of(UserCreateDto userCreateDto){
        return Users.builder()
                .email(userCreateDto.getEmail())
                .username(userCreateDto.getUsername())
                .passwd(userCreateDto.getPasswd())
                .pin(userCreateDto.getPin())
                .build();
    }

}
