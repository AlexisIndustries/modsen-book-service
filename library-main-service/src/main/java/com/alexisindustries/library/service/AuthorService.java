package com.alexisindustries.library.service;

import com.alexisindustries.library.model.Author;

import java.util.List;

public interface AuthorService {
    List<Author> findAll();
    Author findAuthorById(Long id);
    boolean addAuthor(Author author);
    boolean deleteAuthor(Long id);
    boolean updateAuthor(Long id, Author author);
}
