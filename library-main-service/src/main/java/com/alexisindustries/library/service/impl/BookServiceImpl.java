package com.alexisindustries.library.service.impl;

import com.alexisindustries.library.exception.EntityNotFoundException;
import com.alexisindustries.library.mapper.AutoAuthorClassMapper;
import com.alexisindustries.library.mapper.AutoBookClassMapper;
import com.alexisindustries.library.mapper.AutoBookGenreClassMapper;
import com.alexisindustries.library.model.Author;
import com.alexisindustries.library.model.Book;
import com.alexisindustries.library.model.BookGenre;
import com.alexisindustries.library.model.User;
import com.alexisindustries.library.model.dto.BookDto;
import com.alexisindustries.library.model.dto.BookReservationAddDto;
import com.alexisindustries.library.repository.AuthorRepository;
import com.alexisindustries.library.repository.BookGenreRepository;
import com.alexisindustries.library.repository.BookRepository;
import com.alexisindustries.library.service.BookService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookGenreRepository bookGenreRepository;
    private final AuthorRepository authorRepository;
    private final AutoBookClassMapper autoBookClassMapper;
    private final AutoBookGenreClassMapper autoBookGenreClassMapper;
    private final AutoAuthorClassMapper autoAuthorClassMapper;
    private final RestTemplate restTemplate;
    private final String host;

    public BookServiceImpl(BookRepository bookRepository, BookGenreRepository bookGenreRepository, AuthorRepository authorRepository, AutoBookClassMapper autoBookClassMapper, AutoBookGenreClassMapper autoBookGenreClassMapper, AutoAuthorClassMapper autoAuthorClassMapper, RestTemplate restTemplate, @Value("${spring.library.reservation.service.host}") String host) {
        this.bookRepository = bookRepository;
        this.bookGenreRepository = bookGenreRepository;
        this.authorRepository = authorRepository;
        this.autoBookClassMapper = autoBookClassMapper;
        this.autoBookGenreClassMapper = autoBookGenreClassMapper;
        this.autoAuthorClassMapper = autoAuthorClassMapper;
        this.restTemplate = restTemplate;
        this.host = host;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(autoBookClassMapper::mapToBookDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Book with id %s not found", id))
                );
        return autoBookClassMapper.mapToBookDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto findBookByIsbn(String isbn) {
        validateIsbn(isbn);
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Book with isbn %s not found", isbn))
                );
        return autoBookClassMapper.mapToBookDto(book);
    }

    @Override
    @Transactional
    public BookDto addBook(BookDto bookDto) {
        validateIsbn(bookDto.getIsbn());
        Optional<Book> book = bookRepository.findByIsbn(bookDto.getIsbn());
        if (book.isPresent()) {
            throw new EntityExistsException(String.format("Book with isbn %s already exists", bookDto.getIsbn()));
        }

        Book bookToSave = autoBookClassMapper.mapToBook(bookDto);

        List<Author> authors = bookDto.getAuthors().stream()
                .map(authorDto -> authorRepository.findByName(authorDto.getName())
                        .orElseGet(() -> autoAuthorClassMapper.mapToAuthor(authorDto)))
                .collect(Collectors.toList());

        bookToSave.setAuthors(authors);

        List<BookGenre> genres = bookDto.getGenres().stream()
                .map(genreDto -> bookGenreRepository.findByName(genreDto.getName())
                        .orElseGet(() -> autoBookGenreClassMapper.mapToBookGenre(genreDto)))
                .collect(Collectors.toList());

        bookToSave.setGenres(genres);

        Book savedBook = bookRepository.save(bookToSave);

        BookReservationAddDto dto = BookReservationAddDto.builder()
                .bookId(savedBook.getId())
                .build();

        restTemplate.postForObject(host + "/api/v1/reservations", dto, BookReservationAddDto.class);

        return autoBookClassMapper.mapToBookDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto updateBook(Long id, BookDto bookDto) {
        validateIsbn(bookDto.getIsbn());
        Book book = bookRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Book with id %s not found", id))
                );

        if (!book.getIsbn().equals(bookDto.getIsbn())) {
            Optional<Book> bookWithSameIsbn = bookRepository.findByIsbn(bookDto.getIsbn());
            if (bookWithSameIsbn.isPresent()) {
                throw new EntityExistsException(String.format("Book with isbn %s already exists", bookDto.getIsbn()));
            }
        }

        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        List<Author> authors = bookDto.getAuthors().stream()
                .map(authorDto -> authorRepository.findByName(authorDto.getName())
                        .orElseGet(() -> autoAuthorClassMapper.mapToAuthor(authorDto)))
                .collect(Collectors.toList());

        book.setAuthors(authors);

        List<BookGenre> genres = bookDto.getGenres().stream()
                .map(genreDto -> bookGenreRepository.findByName(genreDto.getName())
                        .orElseGet(() -> autoBookGenreClassMapper.mapToBookGenre(genreDto)))
                .collect(Collectors.toList());

        book.setGenres(genres);

        return autoBookClassMapper.mapToBookDto(bookRepository.save(book));
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with id %s not found", id)));
        bookRepository.delete(book);
    }

    private void validateIsbn(String isbn) {
        if (isbn == null || (!isbn.matches("\\d{10}") && !isbn.matches("\\d{13}"))) {
            throw new IllegalArgumentException("Invalid ISBN format. ISBN must be 10 or 13 digits.");
        }
    }
}
