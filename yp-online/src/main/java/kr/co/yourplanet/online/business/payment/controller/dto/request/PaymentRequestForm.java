package kr.co.yourplanet.online.business.payment.controller.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PaymentRequestForm(
        @NotEmpty(message = "orderId는 빈 문자열일 수 없습니다.")
        String orderId,

        @NotNull(message = "amount는 null일 수 없습니다.")
        @Min(value = 0L, message = "amount는 0 이하일 수 없습니다.")
        Long amount
) {
}
