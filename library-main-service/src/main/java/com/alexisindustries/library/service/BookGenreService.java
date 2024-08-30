package com.alexisindustries.library.service;

import com.alexisindustries.library.model.BookGenre;

import java.util.List;

public interface BookGenreService {
    List<BookGenre> findAll();
    BookGenre findBookGenreById(Long id);
    boolean addBookGenre(BookGenre author);
    boolean deleteBookGenre(Long id);
    boolean updateBookGenre(Long id, BookGenre author);
}
