package kr.co.yourplanet.online.business.payment.service;

public interface PaymentRequestService {

    void savePaymentRequest(Long memberId, String orderId, Long amount);
}
