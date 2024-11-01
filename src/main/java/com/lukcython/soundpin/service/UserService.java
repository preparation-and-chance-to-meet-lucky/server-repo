package com.lukcython.soundpin.service;

import com.lukcython.soundpin.config.exception.ExceptionMessage;
import com.lukcython.soundpin.config.exception.NotFoundException;
import com.lukcython.soundpin.config.exception.UserException;
import com.lukcython.soundpin.domain.Playlists;
import com.lukcython.soundpin.domain.Users;
import com.lukcython.soundpin.dto.UserChangeNicknameDto;
import com.lukcython.soundpin.dto.UserCreateDto;
import com.lukcython.soundpin.dto.UserDetailDto;
import com.lukcython.soundpin.dto.UserLoginDto;
import com.lukcython.soundpin.repository.PlaylistRepository;
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
    private final PlaylistRepository playlistRepository;
    private final HttpSession httpSession;

    @Transactional
    public void addSample(){
        UserCreateDto userSample = new UserCreateDto("__sample__", "0000", "Hello World", "__SAMPLE__");
        userRepository.save(Users.of(userSample));
    }

    @Transactional
    public void createUser(UserCreateDto userCreateDto) {
        Optional<Users> user = userRepository.findByUsername(userCreateDto.getUsername());
        //여기서 .isEmpty 사용해도 되는지 확인 필요
        if (user.isEmpty()){
            userRepository.save(Users.of(userCreateDto));
        } else {
            throw new UserException(ExceptionMessage.USER_DUPLICATED);
        }
    }

    public void loginUser(UserLoginDto userLoginDto) {
        Users user = userRepository.findByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        if (Objects.equals(user.getPasswd(), userLoginDto.getPasswd())){
            httpSession.setAttribute("loginUsername", user.getUsername());
            return;
        }
        throw new UserException(ExceptionMessage.USER_UNAUTHORIZATION);
    }

    @Transactional
    public void changeUsername(UserChangeNicknameDto userChangeNicknameDto, String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        if (Objects.equals(httpSession.getAttribute("loginUsername"), username)){
            user.changeNickname(userChangeNicknameDto);
            return ;
        }
        throw new UserException(ExceptionMessage.USER_UNAUTHORIZATION);
    }

    public UserDetailDto getUserData(String username) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.USER_NOT_FOUND));
        return UserDetailDto.of(user);
    }

    public UserDetailDto getUserByPin(String pin) {
        Playlists playlists = playlistRepository.findByPin(pin)
                .orElseThrow(() -> new NotFoundException(ExceptionMessage.PIN_NOT_FOUND));
        return UserDetailDto.of(playlists.getUser());
    }
}
