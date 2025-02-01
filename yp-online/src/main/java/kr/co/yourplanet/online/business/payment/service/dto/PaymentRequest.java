package kr.co.yourplanet.online.business.payment.service.dto;

import lombok.Builder;

@Builder
public record PaymentRequest(
        String paymentKey,
        String orderId,
        Long amount
) {
}
