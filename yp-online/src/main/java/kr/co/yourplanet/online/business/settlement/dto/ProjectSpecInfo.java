package kr.co.yourplanet.online.business.settlement.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;

public record ProjectSpecInfo(

        @Schema(description = "작업 기한")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate deadline,

        @Schema(description = "기본 컷 수")
        int cutCount,

        @Schema(description = "추가 컷 수")
        int additionalCutCount,

        @Schema(description = "최종 컷 수")
        int finalCutCount,

        @Schema(description = "기본 수정 횟수")
        int modificationCount,

        @Schema(description = "추가 수정 횟수")
        int additionalModificationCount,

        @Schema(description = "최종 수정 횟수")
        int finalModificationCount
) {
}
