package com.alexisindustries.library.controller;

import com.alexisindustries.library.model.dto.BookDto;
import com.alexisindustries.library.service.BookService;
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
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
@Tag(name = "Books", description = "API for managing books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Get all books", description = "Returns a list of all books")
    @GetMapping("")
    public ResponseEntity<List<BookDto>> findAllBooks() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @Operation(summary = "Find book by ID", description = "Returns a book by its ID")
    @GetMapping("{id}")
    public ResponseEntity<BookDto> findBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.findBookById(id));
    }

    @Operation(summary = "Find book by ISBN", description = "Returns a book by its ISBN")
    @GetMapping("isbn/{isbn}")
    public ResponseEntity<BookDto> findBookByIsbn(@PathVariable String isbn) {
        return ResponseEntity.ok(bookService.findBookByIsbn(isbn));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Add a new book", description = "Adds a new book to the system")
    @PostMapping("")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(bookDto));
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Delete a book", description = "Deletes a book by its ID")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @SecurityRequirement(name = "bearerAuth")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Update a book", description = "Updates the information of a book by its ID")
    @PatchMapping("{id}")
    public ResponseEntity<BookDto> updateBook(@PathVariable Long id, @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }
}
