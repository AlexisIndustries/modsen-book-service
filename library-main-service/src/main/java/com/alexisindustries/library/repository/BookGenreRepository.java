package com.alexisindustries.library.repository;

import com.alexisindustries.library.model.BookGenre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookGenreRepository extends JpaRepository<BookGenre, Long> {
    Optional<BookGenre> findByName(String name);
}