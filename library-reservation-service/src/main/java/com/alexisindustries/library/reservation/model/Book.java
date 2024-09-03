package com.alexisindustries.library.reservation.model;

import com.alexisindustries.library.reservation.model.Author;
import com.alexisindustries.library.reservation.model.BookGenre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class Book{
    private Long id;
    private String isbn;
    private String title;
    private String description;
    private Integer quantity;
    private List<BookGenre> genres;
    private List<Author> authors;
}