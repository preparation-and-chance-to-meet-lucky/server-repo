package com.lukcython.soundpin.service;

import com.lukcython.soundpin.config.exception.ExceptionMessage;
import com.lukcython.soundpin.config.exception.NotFoundException;
import com.lukcython.soundpin.config.exception.UserException;
import com.lukcython.soundpin.domain.Users;
import com.lukcython.soundpin.dto.UserCreateDto;
import com.lukcython.soundpin.dto.UserLoginDto;
import com.lukcython.soundpin.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    public void createUser(UserCreateDto userCreateDto) {
        Optional<Users> user = userRepository.findByEmail(userCreateDto.getEmail());
        //여기서 .isEmpty 사용해도 되는지 확인 필요
        if (user.isEmpty()){
            userRepository.save(Users.of(userCreateDto));
        } else {
            throw new UserException(ExceptionMessage.USER_DUPLICATED);
        }
    }

    public Boolean loginUser(UserLoginDto userLoginDto) {
        Users users = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        if (Objects.equals(users.getPasswd(), userLoginDto.getPasswd())){
            httpSession.setAttribute("loginUserEmail", users.getEmail());
            return true;
        }
        return false;
    }
}
