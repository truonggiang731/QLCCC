package com.springboot.blog.service.impl;

import com.springboot.blog.entity.User;
import com.springboot.blog.exception.BlogAPIException;
import com.springboot.blog.payload.ResetPasswordDto;
import com.springboot.blog.payload.UserDto;
import com.springboot.blog.repository.HopDongRepository;
import com.springboot.blog.repository.RoleRepository;
import com.springboot.blog.repository.UserRepository;
import com.springboot.blog.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private HopDongRepository hopDongRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository,
                           HopDongRepository hopDongRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hopDongRepository = hopDongRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }
    @Override
    public UserDto getUserByUsernameOrEmail(String name) {
        User user = userRepository.findByUsernameOrEmail(name, name)
                .orElseThrow(() -> new BlogAPIException(HttpStatus.NOT_FOUND,"User not found."));
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUserByRoleId(Long id) {
        return null;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BlogAPIException(HttpStatus.NOT_FOUND,"User not found."));
        user.setAddress(userDto.getAddress());
        user.setName(userDto.getName());
        user.setDateOfBirth(userDto.getDateOfBirth());
        user.setSex(user.getSex());
        user.setPhoneNumber(user.getPhoneNumber());
        User updateUser = userRepository.save(user);
        return modelMapper.map(updateUser, UserDto.class);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BlogAPIException(HttpStatus.NOT_FOUND,"User not found."));
        if(hopDongRepository.findHopDongByUserId(id)!= null){
            throw new BlogAPIException(HttpStatus.NOT_ACCEPTABLE,"cannot delete user because user have hopdong");
        }
        userRepository.delete(user);
    }

    @Override
    public UserDto resetPassword(ResetPasswordDto resetPasswordDto,Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new BlogAPIException(HttpStatus.NOT_FOUND,"User not found."));
        if(!passwordEncoder.matches(resetPasswordDto.getPassword(), user.getPassword())) throw new BlogAPIException(HttpStatus.NOT_ACCEPTABLE,"password incorrect.");
        else if(passwordEncoder.matches(resetPasswordDto.getNewPassword(), user.getPassword())) throw new BlogAPIException(HttpStatus.NOT_ACCEPTABLE,"New password is duplicated.");
        else if(!resetPasswordDto.getConfNewPassword().equals(resetPasswordDto.getNewPassword())) throw new BlogAPIException(HttpStatus.NOT_ACCEPTABLE,"confirm password is not same new password");
        user.setPassword(passwordEncoder.encode(resetPasswordDto.getNewPassword()));
        User updatePass = userRepository.save(user);
        return modelMapper.map(updatePass, UserDto.class);
    }
}
