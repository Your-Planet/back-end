package kr.co.yourplanet.online.business.payment.service.dto;

import java.time.LocalDateTime;

import lombok.Builder;

@Builder
public record PaymentResponse(
        String paymentKey,
        String orderId,
        String orderName,
        String status,
        String method,
        Long totalAmount,
        LocalDateTime requestedAt,
        LocalDateTime approvedAt
) {
}
