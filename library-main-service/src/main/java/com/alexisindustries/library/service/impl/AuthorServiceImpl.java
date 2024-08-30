package com.alexisindustries.library.service.impl;

import com.alexisindustries.library.model.Author;
import com.alexisindustries.library.model.Book;
import com.alexisindustries.library.repository.AuthorRepository;
import com.alexisindustries.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    @Override
    public boolean addAuthor(Author author) {
        if (author.getId() == null) {
            authorRepository.save(author);
            return true;
        }
        Optional<Author> authorOptional = authorRepository.findById(author.getId());
        if (authorOptional.isPresent()) {
            return false;
        }
        authorRepository.save(author);
        return true;
    }

    @Override
    public boolean deleteAuthor(Long id) {
        authorRepository.deleteById(id);
        return !authorRepository.existsById(id);
    }

    @Override
    public boolean updateAuthor(Long id, Author author) {
        Optional<Author> authorToUpdate = authorRepository.findById(id);
        if (authorToUpdate.isPresent()) {
            author.setId(id);
            authorRepository.save(author);
            return true;
        }
        return false;
    }
}
