package com.lukcython.soundpin.service;

import com.lukcython.soundpin.config.exception.ExceptionMessage;
import com.lukcython.soundpin.config.exception.NotFoundException;
import com.lukcython.soundpin.config.exception.UserException;
import com.lukcython.soundpin.domain.User;
import com.lukcython.soundpin.dto.UserChangeNicknameDto;
import com.lukcython.soundpin.domain.Users;
import com.lukcython.soundpin.dto.UserCreateDto;
import com.lukcython.soundpin.dto.UserLoginDto;
import com.lukcython.soundpin.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    @Transactional
    public void addSample(){
        UserCreateDto userSample = new UserCreateDto("__sample__", "0000", "Hello World", "__SAMPLE__");
        userRepository.save(User.of(userSample));
    }

    @Transactional
    public void createUser(UserCreateDto userCreateDto) {
        Optional<User> user = userRepository.findByUsername(userCreateDto.getUsername());
        Optional<Users> user = userRepository.findByEmail(userCreateDto.getEmail());
        //여기서 .isEmpty 사용해도 되는지 확인 필요
        if (user.isEmpty()){
            userRepository.save(Users.of(userCreateDto));
        } else {
            throw new UserException(ExceptionMessage.USER_DUPLICATED);
        }
    }

    public Boolean loginUser(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername())
        Users users = userRepository.findByEmail(userLoginDto.getEmail())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        if (Objects.equals(user.getPasswd(), userLoginDto.getPasswd())){
            httpSession.setAttribute("loginUsername", user.getUsername());
            return true;
        }
        return false;
    }

    @Transactional
    public boolean changeUsername(UserChangeNicknameDto userChangeNicknameDto, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        if ((String)httpSession.getAttribute("loginUsername") == username){
            user.changeNickname(userChangeNicknameDto);
        if (Objects.equals(users.getPasswd(), userLoginDto.getPasswd())){
            httpSession.setAttribute("loginUserEmail", users.getEmail());
            return true;
        }
        return false;
    }
}
