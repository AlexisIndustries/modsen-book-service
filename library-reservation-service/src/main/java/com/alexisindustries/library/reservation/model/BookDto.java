package com.alexisindustries.library.reservation.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDto {
    private Long id;
    private String isbn;
    private String title;
    private String description;
    private Integer quantity;
    private List<BookGenreDto> genres;
    private List<AuthorDto> authors;
}