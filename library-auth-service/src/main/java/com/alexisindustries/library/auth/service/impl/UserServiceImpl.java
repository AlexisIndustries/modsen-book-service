package com.alexisindustries.library.auth.service.impl;

import com.alexisindustries.library.auth.mapper.AutoUserClassMapper;
import com.alexisindustries.library.auth.model.Role;
import com.alexisindustries.library.auth.model.User;
import com.alexisindustries.library.auth.model.dto.RegisterUserDto;
import com.alexisindustries.library.auth.model.dto.UserDto;
import com.alexisindustries.library.auth.repository.UserRepository;
import com.alexisindustries.library.auth.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto saveUser(RegisterUserDto userDto) throws Exception {
        Optional<User> optionalUser = userRepository.findByUsername(userDto.getUsername());

        if (optionalUser.isPresent()) {
            throw new Exception("User " + userDto.getUsername() + " already exists");
        }

        UserDto userDtoToSave = AutoUserClassMapper.MAPPER.mapToUserDto(userDto);

        userDtoToSave.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userDtoToSave.getRoles().add(Role.USER);

        User user = AutoUserClassMapper.MAPPER.mapToUser(userDtoToSave);
        User savedUser = userRepository.save(user);

        return AutoUserClassMapper.MAPPER.mapToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                () -> new Exception("User" + "id" + String.valueOf(id))
        );

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());

        User updatedUser = userRepository.save(user);
        return AutoUserClassMapper.MAPPER.mapToUserDto(updatedUser);
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(AutoUserClassMapper.MAPPER::mapToUserDto).toList();
    }

    @Override
    public UserDto findUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                () -> new Exception("User" + "id" + String.valueOf(id))
        );
        return AutoUserClassMapper.MAPPER.mapToUserDto(user);
    }

    @Override
    public UserDto findUserByUsername(String username) throws Exception {
        return userRepository.findByUsername(username).map(AutoUserClassMapper.MAPPER::mapToUserDto).orElse(null);
    }

    @Override
    public void deleteUserById(Long id) throws Exception {
        User user = userRepository.findById(id).orElseThrow(
                () -> new Exception("User" + "id" + String.valueOf(id))
        );

        userRepository.delete(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        return User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRoles())
                .email(user.getEmail())
                .build();
    }
}
