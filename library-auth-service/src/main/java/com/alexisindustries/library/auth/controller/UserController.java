package com.alexisindustries.library.auth.controller;

import com.alexisindustries.library.auth.model.dto.UserDto;
import com.alexisindustries.library.auth.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Endpoints for managing users")
@SecurityRequirement(name = "bearerAuth")
public class UserController {
    private final UserService userService;

    @Operation(summary = "Find user by ID", description = "Returns a user by their ID")
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> findUserById(@PathVariable Long id) throws Exception {
        UserDto user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Find all users", description = "Returns a list of all users")
    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> findAllUsers() {
        List<UserDto> users = userService.findAllUsers();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @Operation(summary = "Delete user by ID", description = "Deletes a user by their ID")
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserById(@PathVariable Long id) throws Exception {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update user", description = "Updates a user's information")
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
                                              @PathVariable Long id) throws Exception {
        UserDto updatedUser = userService.updateUser(userDto, id);
        return ResponseEntity.ok(updatedUser);
    }
}
