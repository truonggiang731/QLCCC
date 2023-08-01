package com.springboot.blog.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private String name;
    private String username;
    private String email;
    private String password;
    private String dateOfBirth;
    private String address;
    private String sex;
    private String phoneNumber;

}
