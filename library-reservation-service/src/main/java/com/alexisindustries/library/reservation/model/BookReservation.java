package com.alexisindustries.library.reservation.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "books_reservations")
@Getter
@Setter
public class BookReservation {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(nullable = false)
    private Long bookId;
    @Column(nullable = false)
    private Long userId;
    @Column(nullable = false)
    private LocalDateTime borrowedTime;
    @Column(nullable = false)
    private LocalDateTime returnTime;
}
