package com.payflowx.payment_service.exception;

public class IdempotencyConflictException extends RuntimeException {

    public IdempotencyConflictException(String idempotencyKey) {
        super("A request with Idempotency-Key '" + idempotencyKey + "' is still being processed");
    }
}
