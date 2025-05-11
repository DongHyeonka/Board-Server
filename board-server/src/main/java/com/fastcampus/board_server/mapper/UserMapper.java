package com.fastcampus.board_server.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.fastcampus.board_server.dto.UserDto;

@Mapper
public interface UserMapper {
    public UserDto getUserProfile(@Param("id") String id);

    int insertUserProfile(@Param("id") String id, @Param("password") String password, @Param("name") String name,
            @Param("phone") String phone, @Param("address") String address);

    int updateUserProfile(@Param("id") String id, @Param("password") String password, @Param("name") String name,
            @Param("phone") String phone, @Param("address") String address);

    int deleteUserProfile(@Param("id") String id);

    public int register(UserDto userDto);

    public UserDto findByIdAndPassword(@Param("id") String id,
            @Param("password") String password);

    int idCheck(@Param("id") String id);

    public int updatePassword(UserDto userDto);

    public int updateAddress(UserDto userDto);
}
