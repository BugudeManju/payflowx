package com.payflowx.payment_service.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path,
        List<FieldValidationError> fieldErrors
) {
    public record FieldValidationError(String field, String message) {
    }
}
