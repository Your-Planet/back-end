package kr.co.yourplanet.online.business.project.dto.response;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record ContractInfo(
        // 프로젝트 정보
        Long projectId,
        String projectName,
        LocalDateTime acceptDateTime,
        LocalDateTime completeDateTime,
        Long contractAmount,

        // 추가 계약 사항
        AdditionalDetailInfo additionalDetailInfo,

        // 계약 내용
        ContractorInfo client,
        ContractorInfo provider
) {
    @Builder
    public record AdditionalDetailInfo(
            @Schema(description = "작업 기간")
            int workingDays,

            @Schema(description = "컷 수")
            int cuts,

            @Schema(description = "추가 컷 수", nullable = true)
            Integer additionalPanelCount,

            @Schema(description = "수정 횟수")
            int modificationCount,

            @Schema(description = "추가 수정 횟수", nullable = true)
            Integer additionalModificationCount,

            @Schema(description = "원본 파일 제공")
            boolean originalFileOption,

            @Schema(description = "2차 활용 허용")
            boolean refinementOption,

            @Schema(description = "업로드 기간")
            String postDurationMonth,

            @Schema(description = "업로드 연장 기간", nullable = true)
            Integer postDurationExtensionMonths
    ) {
    }

    @Builder
    public record ContractorInfo(
            String companyName,
            String registrationNumber,
            String address,
            String representativeName
    ) {
    }
}
