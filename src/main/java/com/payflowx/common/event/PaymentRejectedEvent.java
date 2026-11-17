package com.payflowx.common.event;

import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentRejectedEvent(UUID paymentId, String reason, LocalDateTime rejectedAt) {
}
