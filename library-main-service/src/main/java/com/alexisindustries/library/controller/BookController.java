package com.alexisindustries.library.controller;

import com.alexisindustries.library.model.Book;
import com.alexisindustries.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping("all")
    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("{id}")
    public Book findBookById(@PathVariable Long id) {
        return bookService.findBookById(id);
    }

    @GetMapping("isbn/{isbn}")
    public Book findBookByIsbn(@PathVariable String isbn) {
        return bookService.findBookByIsbn(isbn);
    }

    @PostMapping("add")
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        if (bookService.addBook(book)) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        if (bookService.deleteBook(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("update/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        if (bookService.updateBook(id, book)) {
            return ResponseEntity.ok(book);
        }
        return ResponseEntity.badRequest().build();
    }
}
