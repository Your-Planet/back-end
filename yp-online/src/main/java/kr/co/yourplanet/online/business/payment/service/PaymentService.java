package kr.co.yourplanet.online.business.payment.service;

public interface PaymentService {

    void approve(Long memberId, Long projectId, String paymentKey, String orderId, Long amount);
}
