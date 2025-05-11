package com.fastcampus.board_server.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.board_server.aop.LoginCheck;
import com.fastcampus.board_server.dto.UserDto;
import com.fastcampus.board_server.dto.request.UserDeleteId;
import com.fastcampus.board_server.dto.request.UserLoginRequest;
import com.fastcampus.board_server.dto.request.UserUpdatePasswordRequest;
import com.fastcampus.board_server.dto.response.LoginResponse;
import com.fastcampus.board_server.dto.response.UserInfoResponse;
import com.fastcampus.board_server.service.UserService;
import com.fastcampus.board_server.utils.SessionUtil;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    private final UserService userService;
    private static final ResponseEntity<LoginResponse> FAIL_RESPONSE = new ResponseEntity<LoginResponse>(BAD_REQUEST);
    private static LoginResponse loginResponse;

    @PostMapping("sign-up")
    @ResponseStatus(CREATED)
    public void signUp(@RequestBody UserDto userDto) {
        if (UserDto.hasNullDataBeforeSignup(userDto)) {
            throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다.");
        }
        userService.register(userDto);
    }

    @PostMapping("sign-in")
    @SuppressWarnings("unused")
    public HttpStatus login(@RequestBody UserLoginRequest loginRequest,
            HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String id = loginRequest.userId();
        String password = loginRequest.password();
        UserDto userInfo = userService.login(id, password);

        if (userInfo == null) {
            return HttpStatus.NOT_FOUND;
        } else if (userInfo != null) {
            loginResponse = LoginResponse.success(userInfo);
            if (userInfo.getStatus() == (UserDto.Status.ADMIN))
                SessionUtil.setLoginAdminId(session, id);
            else
                SessionUtil.setLoginMemberId(session, id);

            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } else {
            throw new RuntimeException("Login Error! 유저 정보가 없거나 지워진 유저 정보입니다.");
        }

        return HttpStatus.OK;
    }

    @GetMapping("my-info")
    public UserInfoResponse memberInfo(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);
        if (id == null)
            id = SessionUtil.getLoginAdminId(session);
        UserDto memberInfo = userService.getUserProfile(id);
        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("logout")
    public void logout(String accountId, HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<LoginResponse> updateUserPassword(String accountId,
            @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
            HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String Id = accountId;
        String beforePassword = userUpdatePasswordRequest.beforePassword();
        String afterPassword = userUpdatePasswordRequest.afterPassword();

        try {
            userService.updatePassword(Id, beforePassword, afterPassword);
            ResponseEntity.ok(new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            log.error("updatePassword 실패", e);
            responseEntity = FAIL_RESPONSE;
        }
        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<LoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId,
            HttpSession session) {
        ResponseEntity<LoginResponse> responseEntity = null;
        String Id = SessionUtil.getLoginMemberId(session);

        try {
            userService.deleteId(Id, userDeleteId.password());
            responseEntity = new ResponseEntity<LoginResponse>(loginResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.info("deleteID 실패");
            responseEntity = FAIL_RESPONSE;
        }
        return responseEntity;
    }
}