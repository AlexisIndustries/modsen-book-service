package com.alexisindustries.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for Book Genre")
public class BookGenreDto {

    @Schema(description = "Unique identifier of the book genre", example = "1")
    private Long id;

    @Schema(description = "Name of the book genre", example = "Science Fiction")
    private String name;
}
