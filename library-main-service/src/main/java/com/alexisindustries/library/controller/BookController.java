package com.alexisindustries.library.controller;

import com.alexisindustries.library.model.Book;
import com.alexisindustries.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("")
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id) {
        Book book = bookService.findBookById(id);
        if(book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @GetMapping("isbn/{isbn}")
    public Book findBookByIsbn(@PathVariable String isbn) {
        return bookService.findBookByIsbn(isbn);
    }

    @PostMapping("")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        if (bookService.addBook(book)) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        if (bookService.updateBook(id, book)) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.badRequest().build();
    }
}
