package com.alexisindustries.library.reservation.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "DTO for Book Reservation")
public class BookReservationDto {
    @Schema(description = "Unique identifier for the book reservation", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private long id;
    @Schema(description = "Unique identifier for the book", example = "101")
    private long bookId;
    @Schema(description = "The time when the book was borrowed", example = "2024-09-12T10:15:30")
    private LocalDateTime borrowedTime;
    @Schema(description = "The time when the book is expected to be returned", example = "2024-09-19T10:15:30")
    private LocalDateTime returnTime;
}
