package com.alexisindustries.library.auth.controller;

import com.alexisindustries.library.auth.mapper.AutoUserClassMapper;
import com.alexisindustries.library.auth.model.Role;
import com.alexisindustries.library.auth.model.User;
import com.alexisindustries.library.auth.model.dto.LoginUserDto;
import com.alexisindustries.library.auth.model.dto.RegisterUserDto;
import com.alexisindustries.library.auth.model.dto.UserDto;
import com.alexisindustries.library.auth.security.jwt.JwtTokenManager;
import com.alexisindustries.library.auth.service.UserService;
import jakarta.ws.rs.core.SecurityContext;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenManager jwtTokenManager;

    @PostMapping("register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegisterUserDto registerUserDto) throws Exception {
        UserDto createdUser = userService.saveUser(registerUserDto);
        createdUser.setPassword("[protected]");
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @PostMapping("login")
    @SuppressWarnings("unchecked")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserDto dto) {
        UserDetails user = userService.loadUserByUsername(dto.getUsername());

        if (passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            Set<Role> authorities = (Set<Role>) user.getAuthorities();
            String token = jwtTokenManager.createToken(user.getUsername(), user.getPassword(), authorities);
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("checkUser")
    public ResponseEntity<UserDto> checkUser() throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken) {
            return ResponseEntity.badRequest().build();
        }
        User user = (User) authentication.getPrincipal();
        UserDto userDto = userService.findUserByUsername(user.getUsername());
        userDto.setPassword("[protected]");
        return ResponseEntity.ok(userDto);
    }
}
