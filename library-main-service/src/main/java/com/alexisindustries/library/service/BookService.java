package com.alexisindustries.library.service;

import com.alexisindustries.library.model.Book;

import java.util.List;

public interface BookService {
    List<Book> findAll();
    Book findBookById(Long id);
    Book findBookByIsbn(String isbn);
    boolean addBook(Book book);
    boolean updateBook(Long id, Book book);
    boolean deleteBook(Long id);
}
