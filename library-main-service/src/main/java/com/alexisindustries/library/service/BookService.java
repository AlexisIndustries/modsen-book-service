package com.alexisindustries.library.service;

import com.alexisindustries.library.model.Book;
import com.alexisindustries.library.model.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> findAll();
    BookDto findBookById(Long id);
    BookDto findBookByIsbn(String isbn);
    BookDto addBook(BookDto bookDto);
    BookDto updateBook(Long id, BookDto bookDto);
    void deleteBook(Long id);
}
