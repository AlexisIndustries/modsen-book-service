package com.alexisindustries.library.service;

import com.alexisindustries.library.model.Author;
import com.alexisindustries.library.model.dto.AuthorDto;

import java.util.List;

public interface AuthorService {
    List<AuthorDto> findAll();
    AuthorDto findAuthorById(Long id);
    AuthorDto addAuthor(AuthorDto author);
    void deleteAuthor(Long id);
    AuthorDto updateAuthor(Long id, AuthorDto author);
}
