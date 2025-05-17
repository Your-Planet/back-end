package kr.co.yourplanet.online.business.settlement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ProjectBasicInfo(

        @Schema(description = "프로젝트 이름")
        String orderTitle,

        @Schema(description = "프로젝트 코드")
        String orderCode
) {
}