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
    private final AutoBookClassMapper autoBookClassMapper;

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
                        () -> new EntityNotFoundException(String.format("Author with id %s not found", id))
                );
        return autoBookClassMapper.mapToBookDto(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDto findBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Author with isbn %s not found", isbn))
                );
        return autoBookClassMapper.mapToBookDto(book);
    }

    @Override
    @Transactional
    public BookDto addBook(BookDto bookDto) {
        Optional<Book> book = bookRepository.findByIsbn(bookDto.getIsbn());
        if (book.isPresent()) {
            throw new EntityExistsException(String.format("Book with isbn %s already exists", bookDto.getId()));
        }

        Book bookToSave = autoBookClassMapper.mapToBook(bookDto);
        Book savedBook = bookRepository.save(bookToSave);
        return autoBookClassMapper.mapToBookDto(savedBook);
    }

    @Override
    @Transactional
    public BookDto updateBook(Long id, BookDto bookDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException(String.format("Book with id %s not found", id))
                );
        book.setId(bookDto.getId());
        book.setIsbn(bookDto.getIsbn());
        book.setTitle(bookDto.getTitle());
        book.setDescription(bookDto.getDescription());
        return autoBookClassMapper.mapToBookDto(bookRepository.save(book));

    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        Book author = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Book with id %s not found", id)));
        bookRepository.delete(author);
    }
}
