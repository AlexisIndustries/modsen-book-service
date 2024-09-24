package com.alexisindustries.library.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Entry to add a book to library inventory")
public class BookReservationAddDto {
    private Long bookId;
}
