package com.springboot.blog.service;

import com.springboot.blog.payload.ResetPasswordDto;
import com.springboot.blog.payload.UserDto;

import java.util.List;

public interface UserService {
    UserDto getUserByUsernameOrEmail(String name);
    List<UserDto> getAllUserByRoleId();
    UserDto updateUser(UserDto userDto, Long id);
    void deleteUser(Long id);
    UserDto resetPassword(ResetPasswordDto resetPasswordDto,Long id);
}
