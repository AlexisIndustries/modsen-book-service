package com.alexisindustries.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "DTO for Book")
public class BookDto {

    @Schema(description = "Unique identifier of the book", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ISBN of the book", example = "9783161484100")
    private String isbn;

    @Schema(description = "Title of the book", example = "Effective Java")
    private String title;

    @Schema(description = "Description of the book", example = "A comprehensive guide to best practices in Java programming.")
    private String description;

    @Schema(description = "Genres of the book")
    private List<BookGenreDto> genres;

    @Schema(description = "Authors of the book")
    private List<AuthorDto> authors;
}
