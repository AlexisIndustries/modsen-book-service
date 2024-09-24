package com.alexisindustries.library.reservation.controller;

import com.alexisindustries.library.reservation.model.dto.BookReservationAddDto;
import com.alexisindustries.library.reservation.model.dto.BookReservationDto;
import com.alexisindustries.library.reservation.service.BookReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Book Reservations", description = "Operations related to book reservations")
public class BookReservationController {
    private final BookReservationService bookReservationService;

    @Autowired
    public BookReservationController(BookReservationService bookReservationService) {
        this.bookReservationService = bookReservationService;
    }

    @Operation(summary = "Get all book reservations", description = "Retrieve a list of all book reservations")
    @GetMapping("")
    public ResponseEntity<List<BookReservationDto>> getAllBookReservations() {
        return ResponseEntity.ok(bookReservationService.findAll());
    }

    @Operation(summary = "Create a new book reservation", description = "Create a new book reservation")
    @PostMapping("")
    public ResponseEntity<BookReservationDto> createBookReservation(@RequestBody BookReservationAddDto bookReservationAddDto) {
        return ResponseEntity.ok(bookReservationService.addBookInventory(bookReservationAddDto));
    }

    @Operation(summary = "Update a book reservation", description = "Update an existing book reservation by its ID")
    @PutMapping("{id}")
    public ResponseEntity<BookReservationDto> updateBookReservation(@PathVariable Long id, @RequestBody BookReservationDto bookReservationDetails) {
        return ResponseEntity.ok(bookReservationService.update(id, bookReservationDetails));
    }

    @Operation(summary = "Get available books", description = "Retrieve a list of all available books")
    @GetMapping("available")
    public ResponseEntity<List<BookReservationDto>> getAvailableBooks() {
        return ResponseEntity.ok(bookReservationService.getAllAvailableBooks());
    }
}
