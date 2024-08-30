package com.alexisindustries.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "authors")
@Getter
@Setter
public class Author implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @ManyToMany(mappedBy = "authors")
    @Cascade(org.hibernate.annotations.CascadeType.MERGE)
    @JsonIgnore
    private List<Book> books;
}
