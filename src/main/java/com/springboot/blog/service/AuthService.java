package com.springboot.blog.service;

import com.springboot.blog.payload.LoginDto;
import com.springboot.blog.payload.UserDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(UserDto registerDto);

//    Long returnRole(LoginDto loginDto);
}
