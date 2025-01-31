package kr.co.yourplanet.online.infra.redis;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.co.yourplanet.online.business.payment.domain.IdempotencyKeyGenerator;
import kr.co.yourplanet.online.business.payment.repository.PaymentRequestRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RedisPaymentRequestRepository implements PaymentRequestRepository {

    private static final String ORDER_KEY_PREFIX = "order:";
    private static final String MEMBER_ID = "memberId";
    private static final String AMOUNT = "amount";
    private static final String IDEMPOTENCY_KEY = "idempotencyKey";
    private static final long TTL_TIME = 600L;  // 10분

    private final RedisRepository redisRepository;
    private final IdempotencyKeyGenerator idempotencyKeyGenerator;

    @Override
    public void save(Long memberId, String orderId, Long amount) {
        String key = getOrderKey(orderId);

        redisRepository.saveHashWithTTL(key, MEMBER_ID, memberId, TTL_TIME);
        redisRepository.saveHashWithTTL(key, AMOUNT, amount, TTL_TIME);
        redisRepository.saveHashWithTTL(key, IDEMPOTENCY_KEY, idempotencyKeyGenerator.generate(orderId), TTL_TIME);
    }

    @Override
    public boolean isExist(String orderId) {
        String key = getOrderKey(orderId);
        return redisRepository.hasKey(key);
    }

    @Override
    public Optional<Long> getAmount(String orderId) {
        String key = getOrderKey(orderId);

        return redisRepository.getHashValue(key, AMOUNT)
                .map(Object::toString)
                .map(Long::parseLong);
    }

    @Override
    public Optional<Long> getOrdererId(String orderId) {
        String key = getOrderKey(orderId);

        return redisRepository.getHashValue(key, MEMBER_ID)
                .map(Object::toString)
                .map(Long::parseLong);
    }

    @Override
    public Optional<String> getIdempotencyKey(String orderId) {
        String key = getOrderKey(orderId);

        return redisRepository.getHashValue(key, IDEMPOTENCY_KEY)
                .map(Object::toString);
    }

    private String getOrderKey(String orderId) {
        return ORDER_KEY_PREFIX + orderId;
    }
}
