package com.alexisindustries.library.auth.exception;

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
