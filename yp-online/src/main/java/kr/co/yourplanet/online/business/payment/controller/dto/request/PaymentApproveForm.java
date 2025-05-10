package kr.co.yourplanet.online.business.payment.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.entity.payment.PaymentType;
import kr.co.yourplanet.core.validation.annotation.ValidEnum;

public record PaymentApproveForm(

        @Schema(examples = { "PROJECT_SETTLEMENT" })
        @ValidEnum(enumClass = PaymentType.class)
        PaymentType paymentType,

        @NotNull(message = "targetId는 null일 수 없습니다.")
        Long targetId,

        @NotBlank(message = "paymentKey는 빈 문자열일 수 없습니다.")
        String paymentKey,

        @NotBlank(message = "orderId는 빈 문자열일 수 없습니다.")
        String orderId,

        @NotNull(message = "amount는 null일 수 없습니다.")
        @Min(value = 0L, message = "amount는 0 이하일 수 없습니다.")
        Long amount
) {
}
