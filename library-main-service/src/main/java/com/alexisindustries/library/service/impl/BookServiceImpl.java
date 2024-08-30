package com.alexisindustries.library.service.impl;

import com.alexisindustries.library.model.Book;
import com.alexisindustries.library.repository.BookRepository;
import com.alexisindustries.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book findBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn).orElse(null);
    }

    @Override
    public boolean addBook(Book book) {
        if (book.getId() == null) {
            bookRepository.save(book);
            return true;
        }
        Optional<Book> bookOptional = bookRepository.findById(book.getId());
        if (bookOptional.isPresent()) {
            return false;
        }
        bookRepository.save(book);
        return true;
    }

    @Override
    public boolean updateBook(Long id, Book book) {
        Optional<Book> bookToUpdate = bookRepository.findById(id);
        if (bookToUpdate.isPresent()) {
            book.setId(id);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteBook(Long id) {
        bookRepository.deleteById(id);
        return !bookRepository.existsById(id);
    }
}
