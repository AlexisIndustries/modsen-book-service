package com.alexisindustries.library.auth.controller;

import com.alexisindustries.library.auth.mapper.AutoUserClassMapper;
import com.alexisindustries.library.auth.model.Role;
import com.alexisindustries.library.auth.model.User;
import com.alexisindustries.library.auth.model.dto.LoginUserDto;
import com.alexisindustries.library.auth.model.dto.RegisterUserDto;
import com.alexisindustries.library.auth.model.dto.UserDto;
import com.alexisindustries.library.auth.security.jwt.JwtTokenManager;
import com.alexisindustries.library.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenManager jwtTokenManager;

    @Operation(summary = "Register a new user", description = "Creates a new user in the system")
    @PostMapping("register")
    public ResponseEntity<UserDto> registerUser(@RequestBody RegisterUserDto dto) throws Exception {
        UserDto createdUser = userService.saveUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Login a user", description = "Authenticates a user and returns a JWT token")
    @PostMapping("login")
    public ResponseEntity<String> loginUser(@RequestBody LoginUserDto dto) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(userService.loginUser(dto));
    }
}
