package com.alexisindustries.library.controller;

import com.alexisindustries.library.model.Author;
import com.alexisindustries.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("")
    public List<Author> findAllBooks() {
        return authorService.findAll();
    }

    @GetMapping("{id}")
    public Author findAuthorById(@PathVariable Long id) {
        return authorService.findAuthorById(id);
    }

    @PostMapping("")
    public ResponseEntity<Author> addAuthor(@RequestBody Author author) {
        if (authorService.addAuthor(author)) {
            return ResponseEntity.ok(author);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        if (authorService.deleteAuthor(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        if (authorService.updateAuthor(id, author)) {
            return ResponseEntity.ok(author);
        }
        return ResponseEntity.badRequest().build();
    }
}
