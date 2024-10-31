package com.lukcython.soundpin.controller;

import com.lukcython.soundpin.config.response.CommonResponse;
import com.lukcython.soundpin.config.response.SingleResponse;
import com.lukcython.soundpin.dto.UserCreateDto;
import com.lukcython.soundpin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<CommonResponse> createUser(@RequestBody UserCreateDto userCreateDto){
        if (userService.createUser(userCreateDto)){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new CommonResponse(true, 201, "회원가입 완료"));
        } else{
            return ResponseEntity.status(HttpStatus.CREATED)
                    //리스폰스 코드 뭐지..??
                    .body(new CommonResponse(false, 444, "회원가입 실패"));
        }

    }

}
