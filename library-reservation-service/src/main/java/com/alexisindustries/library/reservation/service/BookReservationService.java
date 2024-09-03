package com.alexisindustries.library.reservation.service;

import com.alexisindustries.library.reservation.model.Book;
import com.alexisindustries.library.reservation.model.BookReservation;

import java.util.List;
import java.util.Optional;

public interface BookReservationService {
    List<BookReservation> findAll();
    Optional<BookReservation> findById(Long id);
    BookReservation save(BookReservation bookReservation);
    void deleteById(Long id);
    List<Book> getAllAvailableBooks();
    boolean isBookAvailable(long bookId);
}
