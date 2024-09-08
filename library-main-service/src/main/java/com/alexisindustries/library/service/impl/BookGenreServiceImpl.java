package com.alexisindustries.library.service.impl;

import com.alexisindustries.library.model.Author;
import com.alexisindustries.library.model.BookGenre;
import com.alexisindustries.library.repository.BookGenreRepository;
import com.alexisindustries.library.service.BookGenreService;
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
    public List<BookGenre> findAll() {
        return bookGenreRepository.findAll();
    }

    @Override
    public BookGenre findBookGenreById(Long id) {
        return bookGenreRepository.findById(id).orElse(null);
    }

    @Override
    public boolean addBookGenre(BookGenre bookGenre) {
        if (bookGenre.getId() == null) {
            bookGenreRepository.save(bookGenre);
            return true;
        }
        Optional<BookGenre> authorOptional = bookGenreRepository.findById(bookGenre.getId());
        if (authorOptional.isPresent()) {
            return false;
        }
        bookGenreRepository.save(bookGenre);
        return true;
    }

    @Override
    public boolean deleteBookGenre(Long id) {
        bookGenreRepository.deleteById(id);
        return !bookGenreRepository.existsById(id);
    }

    @Override
    public boolean updateBookGenre(Long id, BookGenre bookGenre) {
        Optional<BookGenre> authorToUpdate = bookGenreRepository.findById(id);
        if (authorToUpdate.isPresent()) {
            bookGenre.setId(id);
            bookGenreRepository.save(bookGenre);
            return true;
        }
        return false;
    }
}
