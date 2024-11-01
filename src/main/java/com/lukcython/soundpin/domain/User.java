package com.lukcython.soundpin.domain;

import com.lukcython.soundpin.dto.UserCreateDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String passwd;

    @Builder
    public User(String username, String passwd){
        this.username = username;
        this.passwd = passwd;
    }

    public static User of(UserCreateDto userCreateDto){
        return User.builder()
                .username(userCreateDto.getUsername())
                .passwd(userCreateDto.getPasswd())
                .build();
    }

}
