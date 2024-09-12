package com.alexisindustries.library.reservation.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ExceptionMessageResponse {
    private String message;
    private String cause;
}
