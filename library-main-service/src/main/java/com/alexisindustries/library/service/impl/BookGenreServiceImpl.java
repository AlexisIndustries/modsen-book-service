package com.alexisindustries.library.service.impl;

import com.alexisindustries.library.exception.EntityNotFoundException;
import com.alexisindustries.library.mapper.AutoBookGenreClassMapper;
import com.alexisindustries.library.model.BookGenre;
import com.alexisindustries.library.model.dto.BookGenreDto;
import com.alexisindustries.library.repository.BookGenreRepository;
import com.alexisindustries.library.service.BookGenreService;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookGenreServiceImpl implements BookGenreService {
    private final BookGenreRepository bookGenreRepository;

    @Override
    public List<BookGenreDto> findAll() {
        return bookGenreRepository.findAll().stream().map(AutoBookGenreClassMapper.MAPPER::mapToBookGenreDto).toList();
    }

    @Override
    public BookGenreDto findBookGenreById(Long id) {
        BookGenre bookGenre = bookGenreRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book Genre with id=%s not found.", id)
        );

        return AutoBookGenreClassMapper.MAPPER.mapToBookGenreDto(bookGenre);
    }

    @Override
    public BookGenreDto addBookGenre(BookGenreDto bookGenreDto) {
        Optional<BookGenre> author = bookGenreRepository.findById(bookGenreDto.getId());

        if (author.isPresent()) {
            throw new EntityExistsException(String.format("Book Genre with id %s already exists", bookGenreDto.getId()));
        }

        BookGenre authorToSave = AutoBookGenreClassMapper.MAPPER.mapToBookGenre(bookGenreDto);
        BookGenre savedAuthor = bookGenreRepository.save(authorToSave);
        return AutoBookGenreClassMapper.MAPPER.mapToBookGenreDto(savedAuthor);
    }

    @Override
    public void deleteBookGenre(Long id) {
        BookGenre bookGenre = bookGenreRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book Genre with id=%s not found.", id)
        );
        bookGenreRepository.delete(bookGenre);
    }

    @Override
    public BookGenreDto updateBookGenre(Long id, BookGenreDto bookGenreDto) {
        BookGenre bookGenreToUpdate = bookGenreRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Book Genre with id=%s not found.", id)
        );
        bookGenreToUpdate.setName(bookGenreDto.getName());
        bookGenreToUpdate.setId(bookGenreDto.getId());
        bookGenreRepository.save(bookGenreToUpdate);
        return AutoBookGenreClassMapper.MAPPER.mapToBookGenreDto(bookGenreToUpdate);
    }
}
