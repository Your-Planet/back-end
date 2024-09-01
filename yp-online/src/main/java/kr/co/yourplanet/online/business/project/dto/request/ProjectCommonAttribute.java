package kr.co.yourplanet.online.business.project.dto.request;

import kr.co.yourplanet.core.enums.DemandType;
import kr.co.yourplanet.core.enums.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class ProjectCommonAttribute {

    private ProjectCommonAttribute(){}

    // Nested static classes for additional fields
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectAdditionalPanel {
        @PositiveOrZero(message = "추가 컷 수는 0 이상으로 입력해주세요")
        private Integer count;

        @NotNull
        private Boolean isNegotiable;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectAdditionalModification {
        @NotNull
        @PositiveOrZero(message = "추가 수정 횟수는 0 이상으로 입력해주세요")
        private Integer count;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectOriginFile {
        @NotNull
        @ValidEnum(enumClass = DemandType.class)
        private DemandType demandType;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectRefinement {
        @NotNull
        @ValidEnum(enumClass = DemandType.class)
        private DemandType demandType;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ProjectPostDurationExtension {
        @NotNull
        @PositiveOrZero(message = "업로드 기간 연장은 0 이상으로 입력해주세요")
        private Integer months;
    }
}
