package com.alexisindustries.library.reservation.service.impl;

import com.alexisindustries.library.reservation.exception.EntityNotFoundException;
import com.alexisindustries.library.reservation.mapper.AutoBookReservationClassMapper;
import com.alexisindustries.library.reservation.model.BookReservation;
import com.alexisindustries.library.reservation.model.dto.BookReservationAddDto;
import com.alexisindustries.library.reservation.model.dto.BookReservationDto;
import com.alexisindustries.library.reservation.repository.BookReservationRepository;
import com.alexisindustries.library.reservation.service.BookReservationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookReservationServiceImpl implements BookReservationService {
    private final BookReservationRepository bookReservationRepository;
    private final AutoBookReservationClassMapper autoBookReservationClassMapper;
    private final RestTemplate restTemplate;
    private final String host;

    public BookReservationServiceImpl(BookReservationRepository bookReservationRepository, AutoBookReservationClassMapper mapper, RestTemplate restTemplate, @Value("${spring.library.main.service.host}") String host) {
        this.bookReservationRepository = bookReservationRepository;
        this.autoBookReservationClassMapper = mapper;
        this.restTemplate = restTemplate;
        this.host = host;
    }

    @Override
    public BookReservationDto addBookInventory(BookReservationAddDto bookInventoryAddDto) {
        BookReservation bookReservation = autoBookReservationClassMapper.mapToBookReservation(bookInventoryAddDto);

        //bookReservation.setBorrowedTime(LocalDateTime.now());
        //bookReservation.setReturnTime(LocalDateTime.now().plusMonths(3));

        return autoBookReservationClassMapper.mapToBookReservationDto(bookReservationRepository.save(bookReservation));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookReservationDto> findAll() {
        return bookReservationRepository.findAll().stream().map(autoBookReservationClassMapper::mapToBookReservationDto).toList();
    }

    @Override
    @Transactional
    public List<BookReservationDto> getAllAvailableBooks() {
        List<BookReservation> availableBooks = bookReservationRepository.findByBorrowedTimeIsNullOrReturnTimeBefore(LocalDateTime.now());

        return availableBooks.stream().map(autoBookReservationClassMapper::mapToBookReservationDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookReservationDto update(Long id, BookReservationDto bookReservationDto) {
        Optional<BookReservation> bookReservation = bookReservationRepository.findById(id);
        if (bookReservation.isPresent()) {
            BookReservation updatedBookReservation = bookReservation.get();
            updatedBookReservation.setBookId(bookReservationDto.getBookId());
            updatedBookReservation.setBorrowedTime(bookReservationDto.getBorrowedTime());
            updatedBookReservation.setReturnTime(bookReservationDto.getReturnTime());
            bookReservationRepository.save(updatedBookReservation);
            return autoBookReservationClassMapper.mapToBookReservationDto(updatedBookReservation);
        } else {
            throw new EntityNotFoundException(String.format("Book Reservation with id %s not found", bookReservationDto.getId()));
        }
    }
}
