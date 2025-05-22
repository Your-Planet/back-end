package kr.co.yourplanet.online.business.settlement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record SettlementInfo(

        @Schema(description = "결제액")
        long paymentAmount,

        @Schema(description = "정산액")
        long settlementAmount,

        @Schema(description = "결제 완료 일시")
        LocalDateTime paymentCompletedAt,

        @Schema(description = "계약 완료 일시")
        LocalDateTime contractCompletedAt,

        @Schema(description = "정산 처리 일시")
        LocalDateTime settlementProcessedAt,

        @Schema(description = "결제 상태")
        String paymentStatus,

        @Schema(description = "정산 상태")
        String settlementStatus
) {
}