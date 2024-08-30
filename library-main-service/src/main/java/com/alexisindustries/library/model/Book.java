package com.alexisindustries.library.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
public class Book implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String isbn;

    private String title;

    @Column(length = 1000)
    private String description;

    @Column
    private Integer quantity;

    @ManyToMany
    @JoinTable(
            name = "book_genre",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private List<BookGenre> genres;

    @ManyToMany
    @JoinTable(
            name = "book_author",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    private List<Author> authors;
}