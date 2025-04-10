package kr.co.yourplanet.online.business.payment.service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record PaymentRequest(
    @NotBlank
    String paymentKey,
    @NotBlank
    String orderId,
    @NotNull
    Long amount
) {
}
