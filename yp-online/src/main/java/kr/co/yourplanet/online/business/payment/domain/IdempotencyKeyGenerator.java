package kr.co.yourplanet.online.business.payment.domain;

import java.time.Instant;
import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class IdempotencyKeyGenerator {

    private static final String IDEMPOTENCY_KEY_TEMPLATE = "IDEMPOTENCY-%s-%s-%s";

    public String generate(String orderId) {
        return String.format(IDEMPOTENCY_KEY_TEMPLATE, orderId, Instant.now(), UUID.randomUUID().toString());
    }
}