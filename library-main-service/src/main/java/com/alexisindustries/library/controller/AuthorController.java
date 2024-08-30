package com.alexisindustries.library.controller;

import com.alexisindustries.library.model.Author;
import com.alexisindustries.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/author")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("all")
    public List<Author> findAllBooks() {
        return authorService.findAll();
    }

    @GetMapping("{id}")
    public Author findBookById(@PathVariable Long id) {
        return authorService.findAuthorById(id);
    }

    @PostMapping("add")
    public ResponseEntity<Author> addBook(@RequestBody Author author) {
        if (authorService.addAuthor(author)) {
            return ResponseEntity.ok(author);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (authorService.deleteAuthor(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<Author> updateBook(@PathVariable Long id, @RequestBody Author author) {
        if (authorService.updateAuthor(id, author)) {
            return ResponseEntity.ok(author);
        }
        return ResponseEntity.badRequest().build();
    }
}
