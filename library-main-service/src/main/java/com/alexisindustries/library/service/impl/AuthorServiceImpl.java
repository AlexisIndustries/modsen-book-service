package com.alexisindustries.library.service.impl;

import com.alexisindustries.library.exception.EntityNotFoundException;
import com.alexisindustries.library.mapper.AutoAuthorClassMapper;
import com.alexisindustries.library.model.Author;
import com.alexisindustries.library.model.dto.AuthorDto;
import com.alexisindustries.library.repository.AuthorRepository;
import com.alexisindustries.library.service.AuthorService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorDto> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(AutoAuthorClassMapper.MAPPER::mapToAuthorDto).toList();
    }

    @Override
    public AuthorDto findAuthorById(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Author with id %s not found", id)));
        return AutoAuthorClassMapper.MAPPER.mapToAuthorDto(author);
    }

    @Override
    public AuthorDto addAuthor(AuthorDto authorDto) {
        Optional<Author> author = authorRepository.findById(authorDto.getId());

        if (author.isPresent()) {
            throw new EntityExistsException(String.format("Author with id %s already exists", authorDto.getId()));
        }

        Author authorToSave = AutoAuthorClassMapper.MAPPER.mapToAuthor(authorDto);
        Author savedAuthor = authorRepository.save(authorToSave);
        return AutoAuthorClassMapper.MAPPER.mapToAuthorDto(savedAuthor);
    }

    @Override
    public void deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Author with id %s not found", id)));
        authorRepository.delete(author);
    }

    @Override
    public AuthorDto updateAuthor(Long id, AuthorDto authorDto) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Author with id %s not found", id)));
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        authorRepository.save(author);
        return AutoAuthorClassMapper.MAPPER.mapToAuthorDto(author);
    }
}
