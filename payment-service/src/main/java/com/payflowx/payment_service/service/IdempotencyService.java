package com.payflowx.payment_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IdempotencyService {

    private static final String KEY_PREFIX = "idempotency:";
    private static final String IN_PROGRESS = "IN_PROGRESS";
    private static final Duration TTL = Duration.ofHours(24);

    private final StringRedisTemplate redisTemplate;

    // Check and claim in one atomic Redis step: only the first request with this key gets true
    public boolean claim(String idempotencyKey) {
        Boolean claimed = redisTemplate.opsForValue().setIfAbsent(redisKey(idempotencyKey), IN_PROGRESS, TTL);
        return Boolean.TRUE.equals(claimed);
    }

    public void complete(String idempotencyKey, UUID paymentId) {
        redisTemplate.opsForValue().set(redisKey(idempotencyKey), paymentId.toString(), TTL);
    }

    // Frees the key so the client can retry after a failed attempt
    public void release(String idempotencyKey) {
        redisTemplate.delete(redisKey(idempotencyKey));
    }

    // Empty means the first request with this key is still in progress
    public Optional<UUID> findPaymentId(String idempotencyKey) {
        String value = redisTemplate.opsForValue().get(redisKey(idempotencyKey));
        if (value == null || IN_PROGRESS.equals(value)) {
            return Optional.empty();
        }
        return Optional.of(UUID.fromString(value));
    }

    private String redisKey(String idempotencyKey) {
        return KEY_PREFIX + idempotencyKey;
    }
}
