package com.lukcython.soundpin.controller;

import com.lukcython.soundpin.config.response.CommonResponse;
import com.lukcython.soundpin.config.response.SingleResponse;
import com.lukcython.soundpin.dto.UserCreateDto;
import com.lukcython.soundpin.dto.UserLoginDto;
import com.lukcython.soundpin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public ResponseEntity<SingleResponse<CommonResponse>> echoCheck(){
        userService.addSample();
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "user sample", null));
    }

    /*
    * 회원가입을 시도한다.
    * 동일한 이름의 이메일이 있으면 실패한다.
    * 요구: username, passwd, (pin, email)
    * */
    @PostMapping("/user/signUp")
    public ResponseEntity<SingleResponse<String>> createUser(@RequestBody UserCreateDto userCreateDto){
        try{
            userService.createUser(userCreateDto);
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SingleResponse<>(202, "회원가입 실패", e.toString()));
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new SingleResponse<>(201, "회원가입 완료", "완료!! 드디어!!"));
    }

    /*
    * 로그인은 이메일과 비밀번호로 진행한다.
    * 로그인 성공시 로그인 세션을 부여한다.
    * 요구: username, passwd
    */
    @PostMapping("/user/signIn")
    public ResponseEntity<CommonResponse> loginUser(@RequestBody UserLoginDto userLoginDto){
        if (userService.loginUser(userLoginDto)){
            //HttpStatus 뭘로 해야하지...?
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new CommonResponse(true, 200, "로그인 성공"));
        } else{
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(new CommonResponse(false, 202, "로그인 실패")); //비밀번호 불일치
        }
    }

}
