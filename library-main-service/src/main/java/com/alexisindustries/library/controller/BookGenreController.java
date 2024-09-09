package com.alexisindustries.library.controller;

import com.alexisindustries.library.model.BookGenre;
import com.alexisindustries.library.service.BookGenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/bookgenres")
@RequiredArgsConstructor
public class BookGenreController {
    private final BookGenreService bookGenreService;

    @GetMapping("")
    public List<BookGenre> findAllBooks() {
        return bookGenreService.findAll();
    }

    @GetMapping("{id}")
    public BookGenre findBookById(@PathVariable Long id) {
        return bookGenreService.findBookGenreById(id);
    }

    @PostMapping("")
    public ResponseEntity<BookGenre> addBook(@RequestBody BookGenre author) {
        if (bookGenreService.addBookGenre(author)) {
            return ResponseEntity.ok(author);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (bookGenreService.deleteBookGenre(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<BookGenre> updateBook(@PathVariable Long id, @RequestBody BookGenre bookGenre) {
        if (bookGenreService.updateBookGenre(id, bookGenre)) {
            return ResponseEntity.ok(bookGenre);
        }
        return ResponseEntity.badRequest().build();
    }
}
