package kr.co.yourplanet.online.business.payment.service;

import kr.co.yourplanet.core.entity.payment.PaymentType;

public interface PaymentService {

    void approve(PaymentType paymentType, Long memberId, String paymentKey, String orderId, Long amount, Long targetId);
}
