package com.payflowx.common.event;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentCreatedEvent(
        UUID paymentId,
        BigDecimal amount,
        String currency,
        String customerEmail,
        String status,
        LocalDateTime createdAt
) {
}