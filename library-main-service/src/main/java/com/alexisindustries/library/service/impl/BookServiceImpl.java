package com.alexisindustries.library.service.impl;

import com.alexisindustries.library.exception.EntityNotFoundException;
import com.alexisindustries.library.mapper.AutoBookClassMapper;
import com.alexisindustries.library.mapper.AutoBookGenreClassMapper;
import com.alexisindustries.library.model.Author;
import com.alexisindustries.library.model.Book;
import com.alexisindustries.library.model.dto.BookDto;
import com.alexisindustries.library.repository.BookRepository;
import com.alexisindustries.library.service.BookService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().map(AutoBookClassMapper.MAPPER::mapToBookDto).toList();
    }

    @Override
    public BookDto findBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Author with id %s not found", id))
                );
        return AutoBookClassMapper.MAPPER.mapToBookDto(book);
    }

    @Override
    public BookDto findBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Author with isbn %s not found", isbn))
                );
        return AutoBookClassMapper.MAPPER.mapToBookDto(book);
    }

    @Override
    public BookDto addBook(BookDto bookDto) {
        Optional<Book> book = bookRepository.findById(bookDto.getId());
        if (book.isPresent()) {
            throw new EntityExistsException(String.format("Book with id %s already exists", bookDto.getId()));
        }

        Book bookToSave = AutoBookClassMapper.MAPPER.mapToBook(bookDto);
        Book savedBook = bookRepository.save(bookToSave);
        return AutoBookClassMapper.MAPPER.mapToBookDto(savedBook);
    }

    @Override
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Book with id %s not found", id))
                );
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        return AutoBookClassMapper.MAPPER.mapToBookDto(bookRepository.save(book));

    }

    @Override
    public void deleteBook(Long id) {
        Book author = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with id %s not found", id)));
        bookRepository.delete(author);
    }
}
