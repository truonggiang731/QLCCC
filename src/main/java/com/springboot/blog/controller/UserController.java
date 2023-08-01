package com.springboot.blog.controller;

import com.springboot.blog.payload.ResetPasswordDto;
import com.springboot.blog.payload.UserDto;
import com.springboot.blog.service.UserService;
import com.springboot.blog.service.impl.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private UserService userService;
    public UserController( UserService userService){this.userService = userService;}
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserByUserNameOrEmail(@PathVariable("id") String name){
        return new ResponseEntity<>(userService.getUserByUsernameOrEmail(name), HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return new ResponseEntity<>("user deleted.",HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable Long id){
        UserDto updateUser = userService.updateUser(userDto, id);
        return new ResponseEntity<>(updateUser, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/reset/{id}")
    public ResponseEntity<UserDto> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto, @PathVariable("id") Long id){
        UserDto rsPass = userService.resetPassword(resetPasswordDto, id);
        return new ResponseEntity<>(rsPass,HttpStatus.OK);
    }
}
