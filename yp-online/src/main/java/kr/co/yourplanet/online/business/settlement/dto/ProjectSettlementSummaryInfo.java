package kr.co.yourplanet.online.business.settlement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ProjectSettlementSummaryInfo(

        @Schema(description = "브랜드 파트너 정보")
        SponsorInfo sponsorInfo,

        @Schema(description = "프로젝트 정보")
        ProjectBasicInfo projectBasicInfo,

        @Schema(description = "정산 정보")
        SettlementInfo settlementInfo
) {
}
