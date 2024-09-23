package com.alexisindustries.library.reservation.controller;

import com.alexisindustries.library.reservation.model.dto.BookDto;
import com.alexisindustries.library.reservation.model.dto.BookReservationDto;
import com.alexisindustries.library.reservation.service.BookReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.security.RolesAllowed;
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
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<List<BookReservationDto>> getAllBookReservations() {
        return ResponseEntity.ok(bookReservationService.findAll());
    }

    @Operation(summary = "Get book reservation by ID", description = "Retrieve a book reservation by its ID")
    @GetMapping("{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<BookReservationDto> getBookReservationById(@PathVariable Long id) {
        return ResponseEntity.ok(bookReservationService.findById(id));
    }

    @Operation(summary = "Create a new book reservation", description = "Create a new book reservation")
    @PostMapping("")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<BookReservationDto> createBookReservation(@RequestBody BookReservationDto bookReservationDto) {
        return ResponseEntity.ok(bookReservationService.save(bookReservationDto));
    }

    @Operation(summary = "Update a book reservation", description = "Update an existing book reservation by its ID")
    @PatchMapping("{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<BookReservationDto> updateBookReservation(@PathVariable Long id, @RequestBody BookReservationDto bookReservationDetails) {
        return ResponseEntity.ok(bookReservationService.update(id, bookReservationDetails));
    }

    @Operation(summary = "Delete a book reservation", description = "Delete a book reservation by its ID")
    @DeleteMapping("{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<?> deleteBookReservation(@PathVariable Long id) {
        bookReservationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get available book reservations", description = "Retrieve a list of all available book reservations")
    @GetMapping("available")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<List<BookDto>> getAvailableBookReservations() {
        return ResponseEntity.ok(bookReservationService.getAllAvailableBooks());
    }

    @Operation(summary = "Check if a book is available", description = "Check if a specific book is available for reservation by its ID")
    @GetMapping("isavailable/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<Boolean> isBookAvailable(@PathVariable Long id) {
        return ResponseEntity.ok(bookReservationService.isBookAvailable(id));
    }
}
