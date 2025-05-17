package kr.co.yourplanet.online.business.settlement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record SponsorInfo(

        @Schema(description = "브랜드 파트너명")
        String name,

        @Schema(description = "브랜드 파트너 이메일")
        String email
) {
}
