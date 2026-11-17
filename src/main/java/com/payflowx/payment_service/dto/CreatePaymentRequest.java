package com.payflowx.payment_service.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreatePaymentRequest(

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
        BigDecimal amount,

        @NotBlank(message = "Currency is required")
        @Size(min = 3, max = 3, message = "Currency must be 3 characters")
        String currency,

        @NotBlank(message = "Customer email is required")
        @Email(message = "Customer email must be valid")
        String customerEmail,

        @Size(max = 500, message = "Description cannot exceed 500 characters")
        String description
) {
}