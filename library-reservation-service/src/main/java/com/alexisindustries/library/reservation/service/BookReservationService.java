package com.alexisindustries.library.reservation.service;

import com.alexisindustries.library.reservation.model.dto.BookDto;
import com.alexisindustries.library.reservation.model.dto.BookReservationDto;

import java.util.List;

public interface BookReservationService {
    List<BookReservationDto> findAll();
    BookReservationDto findById(Long id);
    BookReservationDto save(BookReservationDto bookReservationDto);
    void deleteById(Long id);
    List<BookDto> getAllAvailableBooks();
    boolean isBookAvailable(long bookId);

    BookReservationDto update(Long id, BookReservationDto bookReservationDto);
}
