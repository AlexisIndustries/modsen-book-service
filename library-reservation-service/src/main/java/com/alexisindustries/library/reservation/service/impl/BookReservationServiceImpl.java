package com.alexisindustries.library.reservation.service.impl;

import com.alexisindustries.library.reservation.exception.EntityAlreadyExistsException;
import com.alexisindustries.library.reservation.exception.EntityNotFoundException;
import com.alexisindustries.library.reservation.mapper.AutoBookReservationClassMapper;
import com.alexisindustries.library.reservation.model.dto.BookDto;
import com.alexisindustries.library.reservation.model.BookReservation;
import com.alexisindustries.library.reservation.model.dto.BookReservationDto;
import com.alexisindustries.library.reservation.repository.BookReservationRepository;
import com.alexisindustries.library.reservation.service.BookReservationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
    public List<BookReservationDto> findAll() {
        return bookReservationRepository.findAll().stream().map(autoBookReservationClassMapper::mapToBookReservationDto).toList();
    }

    @Override
    public BookReservationDto findById(Long id) {
        BookReservation bookReservation = bookReservationRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Book Reservation with id %s not found", id))
                );
        return autoBookReservationClassMapper.mapToBookReservationDto(bookReservation);
    }

    @Override
    public BookReservationDto save(BookReservationDto bookReservationDto) {
        Optional<BookReservation> bookReservation = bookReservationRepository.findById(bookReservationDto.getId());
        if (bookReservation.isPresent()) {
            throw new EntityAlreadyExistsException(String.format("Book Reservation with id %s already exists", bookReservationDto.getId()));
        }
        ResponseEntity<BookDto> bookOptionalResponseEntity = restTemplate.getForEntity(host + "/api/v1/books/" + bookReservationDto.getBookId(), BookDto.class);
        if (bookOptionalResponseEntity.getStatusCode().is2xxSuccessful()) {
            BookReservation bookReservationToSave = autoBookReservationClassMapper.mapToBookReservation(bookReservationDto);
            BookReservation savedBookReservation = bookReservationRepository.save(bookReservationToSave);
            return autoBookReservationClassMapper.mapToBookReservationDto(savedBookReservation);
        }
        throw new EntityNotFoundException(String.format("Book with id %s not found", bookReservationDto.getBookId()));
    }

    @Override
    public void deleteById(Long id) {
        BookReservation bookReservation = bookReservationRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Book Reservation with id %s not found", id))
                );
        bookReservationRepository.deleteById(id);
    }

    @Override
    public List<BookDto> getAllAvailableBooks() {
        ResponseEntity<BookDto[]> booksRequest = restTemplate.getForEntity("http://localhost:8080/api/v1/book/all", BookDto[].class);
        BookDto[] bookDto = Objects.requireNonNull(booksRequest.getBody());
        List<BookDto> availableBookDto = new ArrayList<>();
        for (BookDto bookDto1 : bookDto) {
            long id = bookDto1.getId();
            int count = bookReservationRepository.countBookReservationByBookId(id);
            if (bookDto1.getQuantity() > count) {
                availableBookDto.add(bookDto1);
            }
        }
        return availableBookDto;
    }

    @Override
    public boolean isBookAvailable(long bookId) {
        ResponseEntity<BookDto> booksRequest = restTemplate.getForEntity("http://localhost:8080/api/v1/book/" + bookId, BookDto.class);
        if (booksRequest.getStatusCode().is4xxClientError()) {
            return false;
        }
        BookDto bookDto = Objects.requireNonNull(booksRequest.getBody());
        long id = bookDto.getId();
        int count = bookReservationRepository.countBookReservationByBookId(id);
        return bookDto.getQuantity() > count;
    }

    @Override
    public BookReservationDto update(Long id, BookReservationDto bookReservationDto) {
        Optional<BookReservation> bookReservation = bookReservationRepository.findById(id);
        if (bookReservation.isPresent()) {
            ResponseEntity<BookDto> bookOptionalResponseEntity = restTemplate.getForEntity(host + "/api/v1/books/" + bookReservation.get().getBookId(), BookDto.class);
            if (bookOptionalResponseEntity.getStatusCode().is4xxClientError()) {
                throw new EntityNotFoundException(String.format("Book Reservation with id %s not found", bookReservationDto.getId()));
            }
            BookReservation updatedBookReservation = bookReservation.get();
            updatedBookReservation.setBookId(bookReservationDto.getBookId());
            updatedBookReservation.setUserId(bookReservationDto.getUserId());
            updatedBookReservation.setBorrowedTime(bookReservationDto.getBorrowedTime());
            updatedBookReservation.setReturnTime(bookReservationDto.getReturnTime());
            bookReservationRepository.save(updatedBookReservation);
            return autoBookReservationClassMapper.mapToBookReservationDto(updatedBookReservation);
        } else {
            throw new EntityNotFoundException(String.format("Book Reservation with id %s not found", bookReservationDto.getId()));
        }
    }
}
