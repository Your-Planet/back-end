package kr.co.yourplanet.online.business.payment.service;

public interface PaymentRequestService {

    void save(Long memberId, String orderId, Long amount);

    void checkIfExists(String orderId);

    void checkIfNotExists(String orderId);

    void checkIfOrderMatches(String orderId, Long memberId);

    void checkIfAmountMatches(String orderId, Long amount);
}
