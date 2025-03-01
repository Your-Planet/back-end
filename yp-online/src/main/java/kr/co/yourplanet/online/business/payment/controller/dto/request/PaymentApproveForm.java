package kr.co.yourplanet.online.business.payment.controller.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PaymentApproveForm(

        @NotNull(message = "projectId는 null일 수 없습니다.")
        Long projectId,

        @NotBlank(message = "paymentKey는 빈 문자열일 수 없습니다.")
        String paymentKey,

        @NotBlank(message = "orderId는 빈 문자열일 수 없습니다.")
        String orderId,

        @NotNull(message = "amount는 null일 수 없습니다.")
        @Min(value = 0L, message = "amount는 0 이하일 수 없습니다.")
        Long amount
) {
}
