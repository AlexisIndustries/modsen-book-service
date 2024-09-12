package com.alexisindustries.library.service;

import com.alexisindustries.library.model.BookGenre;
import com.alexisindustries.library.model.dto.BookGenreDto;

import java.util.List;

public interface BookGenreService {
    List<BookGenreDto> findAll();
    BookGenreDto findBookGenreById(Long id);
    BookGenreDto addBookGenre(BookGenreDto bookGenreDto);
    void deleteBookGenre(Long id);
    BookGenreDto updateBookGenre(Long id, BookGenreDto bookGenreDto);
}
