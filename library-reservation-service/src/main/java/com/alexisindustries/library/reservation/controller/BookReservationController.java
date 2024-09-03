package com.alexisindustries.library.reservation.controller;

import com.alexisindustries.library.reservation.model.Book;
import com.alexisindustries.library.reservation.model.BookReservation;
import com.alexisindustries.library.reservation.service.BookReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/book/reservation")
public class BookReservationController {
    private final BookReservationService bookReservationService;
    private final RestTemplate restTemplate;
    private final String host;

    @Autowired
    public BookReservationController(BookReservationService bookReservationService, RestTemplate restTemplate, @Value("${spring.library.auth.service.host}")  String host) {
        this.bookReservationService = bookReservationService;
        this.restTemplate = restTemplate;
        this.host = host;
    }

    @GetMapping
    public List<BookReservation> getAllBookReservations() {
        return bookReservationService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookReservation> getBookReservationById(@PathVariable Long id) {
        Optional<BookReservation> bookReservation = bookReservationService.findById(id);
        return bookReservation.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("add")
    public ResponseEntity<BookReservation> createBookReservation(@RequestBody BookReservation bookReservation) {
        ResponseEntity<Book> bookOptionalResponseEntity = restTemplate.getForEntity(host + "/api/v1/book/" + bookReservation.getBookId(), Book.class);
        if (bookOptionalResponseEntity.getStatusCode().is2xxSuccessful()) {
            BookReservation bookReservationSaved = bookReservationService.save(bookReservation);
            return ResponseEntity.ok(bookReservationSaved);
        }
        return ResponseEntity.badRequest().build();
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<BookReservation> updateBookReservation(@PathVariable Long id, @RequestBody BookReservation bookReservationDetails) {
        Optional<BookReservation> bookReservation = bookReservationService.findById(id);
        if (bookReservation.isPresent()) {
            ResponseEntity<Book> bookOptionalResponseEntity = restTemplate.getForEntity(host + "/api/v1/book/" + bookReservation.get().getBookId(), Book.class);
            if (bookOptionalResponseEntity.getStatusCode().is4xxClientError()) {
                return ResponseEntity.badRequest().build();
            }
            BookReservation updatedBookReservation = bookReservation.get();
            updatedBookReservation.setBookId(bookReservationDetails.getBookId());
            updatedBookReservation.setUserId(bookReservationDetails.getUserId());
            updatedBookReservation.setBorrowedTime(bookReservationDetails.getBorrowedTime());
            updatedBookReservation.setReturnTime(bookReservationDetails.getReturnTime());
            bookReservationService.save(updatedBookReservation);
            return ResponseEntity.ok(updatedBookReservation);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteBookReservation(@PathVariable Long id) {
        bookReservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
