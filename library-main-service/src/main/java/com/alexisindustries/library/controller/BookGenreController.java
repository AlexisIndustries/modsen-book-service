package com.alexisindustries.library.controller;

import com.alexisindustries.library.model.dto.BookGenreDto;
import com.alexisindustries.library.service.BookGenreService;
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
@RequestMapping("/api/v1/bookgenres")
@RequiredArgsConstructor
@Tag(name = "Book Genres", description = "API for managing book genres")
public class BookGenreController {
    private final BookGenreService bookGenreService;

    @Operation(summary = "Get all book genres", description = "Returns a list of all book genres")
    @GetMapping("")
    public ResponseEntity<List<BookGenreDto>> findAllBooks() {
        return ResponseEntity.ok(bookGenreService.findAll());
    }

    @Operation(summary = "Find book genre by ID", description = "Returns a book genre by its ID")
    @GetMapping("{id}")
    public ResponseEntity<BookGenreDto> findBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookGenreService.findBookGenreById(id));
    }

    @Operation(summary = "Add a new book genre", description = "Adds a new book genre to the system")
    @PostMapping("")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BookGenreDto> addBook(@RequestBody BookGenreDto bookGenreDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookGenreService.addBookGenre(bookGenreDto));
    }

    @Operation(summary = "Delete a book genre", description = "Deletes a book genre by its ID")
    @DeleteMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookGenreService.deleteBookGenre(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update a book genre", description = "Updates the information of a book genre by its ID")
    @PutMapping("{id}")
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<BookGenreDto> updateBook(@PathVariable Long id, @RequestBody BookGenreDto bookGenreDto) {
        return ResponseEntity.ok(bookGenreService.updateBookGenre(id, bookGenreDto));
    }
}
