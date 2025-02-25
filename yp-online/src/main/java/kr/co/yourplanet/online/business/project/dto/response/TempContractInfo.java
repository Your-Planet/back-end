package kr.co.yourplanet.online.business.project.dto.response;

import java.time.LocalDateTime;

import kr.co.yourplanet.core.enums.PostDurationMonthType;
import lombok.Builder;

@Builder
public record TempContractInfo(
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
            int workingDays,
            int cuts,
            int modificationCount,
            // 추가 수정 횟수
            // 원본 파일 제공
            // 2차 활용 허용
            PostDurationMonthType postDurationMonthType
            // 업로드 기간 연장
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
