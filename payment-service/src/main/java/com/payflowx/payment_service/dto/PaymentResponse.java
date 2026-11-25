package com.payflowx.payment_service.dto;

import com.payflowx.payment_service.enums.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID paymentId,
        BigDecimal amount,
        String currency,
        PaymentStatus status,
        String customerEmail,
        String description,
        LocalDateTime createdAt
) {
}