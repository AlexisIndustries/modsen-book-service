package com.alexisindustries.library.auth.service;

import com.alexisindustries.library.auth.model.dto.LoginUserDto;
import com.alexisindustries.library.auth.model.dto.RegisterUserDto;
import com.alexisindustries.library.auth.model.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto saveUser(RegisterUserDto registerUserDto) throws Exception;
    void deleteUserById(Long id) throws Exception;
    UserDto updateUser(UserDto userDto, Long id) throws Exception;
    List<UserDto> findAllUsers();
    UserDto findUserById(Long id) throws Exception;
    UserDto findUserByUsername(String username) throws Exception;
    String loginUser(LoginUserDto loginUserDto) throws Exception;
}
