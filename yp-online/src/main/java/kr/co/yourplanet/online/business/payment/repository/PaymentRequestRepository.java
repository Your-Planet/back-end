package kr.co.yourplanet.online.business.payment.repository;

import java.util.Optional;

public interface PaymentRequestRepository {

    void save(Long memberId, String orderId, Long amount);

    boolean isExist(String orderId);

    Optional<Long> getAmount(String orderId);

    Optional<Long> getOrdererId(String orderId);

    Optional<String> getIdempotencyKey(String orderId);
}
