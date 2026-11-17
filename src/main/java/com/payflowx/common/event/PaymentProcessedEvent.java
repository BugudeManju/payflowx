package com.payflowx.common.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentProcessedEvent(UUID paymentId, boolean success, String failureReason, LocalDateTime processedAt) {
}
