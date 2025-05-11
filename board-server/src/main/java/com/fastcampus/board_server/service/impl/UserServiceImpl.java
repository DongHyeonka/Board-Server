package com.fastcampus.board_server.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.fastcampus.board_server.dto.UserDto;
import com.fastcampus.board_server.exception.DuplicatedIdException;
import com.fastcampus.board_server.mapper.UserMapper;
import com.fastcampus.board_server.service.UserService;
import com.fastcampus.board_server.utils.SHA256Util;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public void register(UserDto userDto) {
        boolean duplIdResult = isDuplicated(userDto.getUserId());
        if (duplIdResult) {
            throw new DuplicatedIdException("중복된 아이디입니다.");
        }
        userDto.setCreateTime(new Date());
        userDto.setPassword(SHA256Util.encryptSHA256(userDto.getPassword()));
        int insertCount = userMapper.register(userDto);

        if (insertCount != 1) {
            log.error("insertMember ERROR! {}", userDto);
            throw new RuntimeException(
                    "insertUser ERROR! 회원가입 메서드를 확인해주세요\n" + "Params : " + userDto);
        }
    }

    @Override
    public UserDto login(String id, String password) {
        String cryptoPassword = SHA256Util.encryptSHA256(password);
        UserDto memberInfo = userMapper.findByIdAndPassword(id, cryptoPassword);
        return memberInfo;
    }

    @Override
    public boolean isDuplicated(String id) {
        return userMapper.idCheck(id) == 1;
    }

    @Override
    public UserDto getUserProfile(String id) {
        return userMapper.getUserProfile(id);
    }

    @Override
    public void updatePassword(String id, String beforePassword, String newPassword) {
        String cryptoPassword = SHA256Util.encryptSHA256(beforePassword);
        UserDto memberInfo = userMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            memberInfo.setPassword(SHA256Util.encryptSHA256(newPassword));
            int insertCount = userMapper.updatePassword(memberInfo);
        } else {
            log.error("updatePasswrod ERROR! {}", memberInfo);
            throw new IllegalArgumentException(
                    "updatePasswrod ERROR! 비밀번호 변경 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }

    @Override
    public void deleteId(String id, String passWord) {
        String cryptoPassword = SHA256Util.encryptSHA256(passWord);
        UserDto memberInfo = userMapper.findByIdAndPassword(id, cryptoPassword);

        if (memberInfo != null) {
            userMapper.deleteUserProfile(memberInfo.getUserId());
        } else {
            log.error("deleteId ERROR! {}", memberInfo);
            throw new RuntimeException("deleteId ERROR! id 삭제 메서드를 확인해주세요\n" + "Params : " + memberInfo);
        }
    }
    
}
