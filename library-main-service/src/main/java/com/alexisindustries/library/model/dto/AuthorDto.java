package com.alexisindustries.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO for Author")
public class AuthorDto {
    @Schema(description = "Unique identifier of the author", example = "1")
    private Long id;

    @Schema(description = "Name of the author", example = "John Doe")
    private String name;
}
