package com.alexisindustries.library.reservation.service;

import com.alexisindustries.library.reservation.model.dto.BookReservationAddDto;
import com.alexisindustries.library.reservation.model.dto.BookReservationDto;

import java.util.List;

public interface BookReservationService {
    BookReservationDto addBookInventory(BookReservationAddDto bookInventoryAddDto);

    List<BookReservationDto> findAll();
    List<BookReservationDto> getAllAvailableBooks();
    BookReservationDto update(Long id, BookReservationDto bookReservationDto);
}
