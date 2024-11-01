package com.lukcython.soundpin.service;

import com.lukcython.soundpin.domain.User;
import com.lukcython.soundpin.dto.UserCreateDto;
import com.lukcython.soundpin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Boolean createUser(UserCreateDto userCreateDto) {
        Optional<User> user = userRepository.findByEmail(userCreateDto.getEmail());
        if (user.isEmpty()){
            userRepository.save(User.of(userCreateDto));
            return true;
        }
        else{
            return false;
        }
    }
}
