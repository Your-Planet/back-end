package kr.co.yourplanet.online.business.payment.repository;

public interface PaymentRequestRepository {

    void save(Long memberId, String orderId, Long amount);

    boolean isExist(String orderId);

    Long getAmount(String orderId);

    Long getOrdererId(String orderId);

    String getIdempotencyKey(String orderId);
}
