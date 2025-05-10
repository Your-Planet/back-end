package kr.co.yourplanet.online.business.payment.service.processor;

import kr.co.yourplanet.online.business.payment.service.dto.PaymentResponse;

public interface PaymentProcessor {

    void validate(Long targetId, Long memberId);

    void afterPayment(PaymentResponse response, Long targetId);
}
