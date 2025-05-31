package kr.co.yourplanet.online.infra.redis;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import kr.co.yourplanet.online.business.payment.util.IdempotencyKeyGenerator;
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

    @Override
    public void save(Long memberId, String orderId, Long amount) {
        String key = getOrderKey(orderId);

        redisRepository.saveHash(key, MEMBER_ID, memberId, TTL_TIME);
        redisRepository.saveHash(key, AMOUNT, amount, TTL_TIME);
        redisRepository.saveHash(key, IDEMPOTENCY_KEY, IdempotencyKeyGenerator.generate(orderId), TTL_TIME);
    }

    @Override
    public boolean isExist(String orderId) {
        String key = getOrderKey(orderId);
        return redisRepository.hasHashKey(key);
    }

    // TODO: 한꺼번에 해시 구조 가져오는 방식으로 리팩터링
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
