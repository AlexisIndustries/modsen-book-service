package com.alexisindustries.library.controller;

import com.alexisindustries.library.model.Author;
import com.alexisindustries.library.model.dto.AuthorDto;
import com.alexisindustries.library.service.AuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
@Tag(name = "Authors", description = "API for managing authors")
public class AuthorController {
    private final AuthorService authorService;

    @Operation(summary = "Get all authors", description = "Returns a list of all authors")
    @GetMapping("")
    public ResponseEntity<List<AuthorDto>> findAllAuthors() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @Operation(summary = "Find author by ID", description = "Returns an author by their ID")
    @GetMapping("{id}")
    public AuthorDto findAuthorById(@PathVariable Long id) {
        return authorService.findAuthorById(id);
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Add a new author", description = "Adds a new author to the system")
    @PostMapping("")
    public ResponseEntity<AuthorDto> addAuthor(@RequestBody AuthorDto author) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorService.addAuthor(author));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete an author", description = "Deletes an author by their ID")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.noContent().build();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update an author", description = "Updates the information of an author by their ID")
    @PutMapping("{id}")
    public ResponseEntity<AuthorDto> updateAuthor(@PathVariable Long id, @RequestBody AuthorDto author) {
        return ResponseEntity.ok(authorService.updateAuthor(id, author));
    }
}
