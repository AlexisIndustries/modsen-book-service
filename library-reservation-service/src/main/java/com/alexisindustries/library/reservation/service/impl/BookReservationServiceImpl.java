package com.alexisindustries.library.reservation.service.impl;

import com.alexisindustries.library.reservation.model.Role;
import com.alexisindustries.library.reservation.model.Book;
import com.alexisindustries.library.reservation.model.BookReservation;
import com.alexisindustries.library.reservation.repository.BookReservationRepository;
import com.alexisindustries.library.reservation.service.BookReservationService;
import jakarta.annotation.security.RolesAllowed;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@AllArgsConstructor
@Service
@Transactional
public class BookReservationServiceImpl implements BookReservationService {
    private final BookReservationRepository bookReservationRepository;
    private final RestTemplate restTemplate;

    @Override
    @RolesAllowed({"ADMIN", "USER"})
    public List<BookReservation> findAll() {
        return bookReservationRepository.findAll();
    }

    @Override
    @RolesAllowed({"ADMIN", "USER"})
    public Optional<BookReservation> findById(Long id) {
        return bookReservationRepository.findById(id);
    }

    @Override
    @RolesAllowed({"ADMIN", "USER"})
    public BookReservation save(BookReservation bookReservation) {
        return bookReservationRepository.save(bookReservation);
    }

    @Override
    @RolesAllowed({"ADMIN", "USER"})
    public void deleteById(Long id) {
        bookReservationRepository.deleteById(id);
    }

    @Override
    @RolesAllowed({"ADMIN", "USER"})
    public List<Book> getAllAvailableBooks() {
        ResponseEntity<Book[]> booksRequest = restTemplate.getForEntity("http://localhost:8080/api/v1/book/all", Book[].class);
        Book[] books = Objects.requireNonNull(booksRequest.getBody());
        List<Book> availableBooks = new ArrayList<>();
        for (Book book : books) {
            long id = book.getId();
            int count = bookReservationRepository.countBookReservationByBookId(id);
            if (book.getQuantity() > count) {
                availableBooks.add(book);
            }
        }
        return availableBooks;
    }

    @Override
    @RolesAllowed({"ADMIN", "USER"})
    public boolean isBookAvailable(long bookId) {
        ResponseEntity<Book> booksRequest = restTemplate.getForEntity("http://localhost:8080/api/v1/book/" + bookId, Book.class);
        if (booksRequest.getStatusCode().is4xxClientError()) {
            return false;
        }
        Book book = Objects.requireNonNull(booksRequest.getBody());
        long id = book.getId();
        int count = bookReservationRepository.countBookReservationByBookId(id);
        return book.getQuantity() > count;
    }
}
