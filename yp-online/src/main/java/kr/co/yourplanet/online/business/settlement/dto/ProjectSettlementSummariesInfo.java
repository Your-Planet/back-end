package kr.co.yourplanet.online.business.settlement.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.co.yourplanet.core.model.PageInfo;
import lombok.Builder;

@Builder
public record ProjectSettlementSummariesInfo(

        @Schema(description = "프로젝트 정산 요약 정보")
        List<ProjectSettlementSummaryInfo> projectSettlementSummaryInfo,

        @Schema(description = "페이지 정보")
        PageInfo pageInfo
) {
}
