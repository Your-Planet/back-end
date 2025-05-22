package kr.co.yourplanet.online.business.payment.util;

import java.time.Instant;
import java.util.UUID;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class IdempotencyKeyGenerator {

    private static final String IDEMPOTENCY_KEY_TEMPLATE = "IDEMPOTENCY-%s-%s-%s";

    public static String generate(String orderId) {
        return String.format(IDEMPOTENCY_KEY_TEMPLATE, orderId, Instant.now(), UUID.randomUUID().toString());
    }
}