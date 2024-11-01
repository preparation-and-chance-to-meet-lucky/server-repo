package com.lukcython.soundpin.controller;

import com.lukcython.soundpin.config.response.CommonResponse;
import com.lukcython.soundpin.config.response.SingleResponse;
import com.lukcython.soundpin.dto.UserChangeNicknameDto;
import com.lukcython.soundpin.dto.UserCreateDto;
import com.lukcython.soundpin.dto.UserDetailDto;
import com.lukcython.soundpin.dto.UserLoginDto;
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


    @GetMapping("/user")
    public ResponseEntity<SingleResponse<CommonResponse>> echoCheck(){
        userService.addSample();
        return ResponseEntity.ok()
                .body(new SingleResponse<>(200, "user sample was made", null));
    }

    /*
    * 회원가입을 시도한다.
    * 동일한 이름의 이메일이 있으면 실패한다.
    * 요구: username, passwd
    * */
    @PostMapping("/user/signUp")
    public ResponseEntity<SingleResponse<String>> createUser(@RequestBody UserCreateDto userCreateDto){
        userService.createUser(userCreateDto);
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
        userService.loginUser(userLoginDto);
        //HttpStatus 뭘로 해야하지...?
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse(true, 200, "로그인 성공"));

    }

    @GetMapping("user/get/{username}")
    public ResponseEntity<SingleResponse<UserDetailDto>> getUserData(@PathVariable("username") String username){
        UserDetailDto userDetailDto = userService.getUserData(username);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SingleResponse<>(200, "데이더 조회 성공", userDetailDto));
    }

    @GetMapping("user/getby/{pin}")
    public ResponseEntity<SingleResponse<UserDetailDto>> getUserByPin(@PathVariable("pin") String pin){
        UserDetailDto userDetailDto = userService.getUserByPin(pin);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new SingleResponse<>(200, "데이터 조회 성공", userDetailDto));
    }

    @PutMapping("/user/setNickname/{username}")
    public ResponseEntity<SingleResponse<String>> changeUsername(@PathVariable("username") String username,
                                                                 @RequestBody UserChangeNicknameDto userChangeNicknameDto){
        if (userService.changeUsername(userChangeNicknameDto, username)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SingleResponse<>(200, "수정 성공", "수정 성공"));
        }
        else{
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new SingleResponse<>(202, "수정 실패", "수정 권한이 없습니다. "));
        }
    }
}
