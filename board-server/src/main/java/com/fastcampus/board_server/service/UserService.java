package com.fastcampus.board_server.service;

import com.fastcampus.board_server.dto.UserDto;

public interface UserService {
    void register(UserDto userProfile);

    UserDto login(String id, String password);

    boolean isDuplicated(String id);

    UserDto getUserProfile(String id);

    void updatePassword(String id, String beforePassword, String newPassword);

    void deleteId(String id, String password);
}
