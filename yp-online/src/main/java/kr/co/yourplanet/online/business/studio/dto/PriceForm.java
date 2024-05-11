package kr.co.yourplanet.online.business.studio.dto;

import kr.co.yourplanet.core.enums.PostDurationMonthType;
import kr.co.yourplanet.core.enums.ProvisionType;
import kr.co.yourplanet.core.enums.ValidEnum;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceForm {
    @Valid
    @NotNull
    private DefaultOption service;
    @Valid
    @NotNull
    private AdditionalPriceForm option;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DefaultOption {
        private int price;
        private int workingDays;
        private int defaultCuts;
        private int modificationCount;
        @ValidEnum(enumClass = PostDurationMonthType.class)
        private PostDurationMonthType postDurationMonthType;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdditionalPriceForm {
        @Valid
        @NotNull
        private AdditionalPanel additionalPanel;
        @Valid
        @NotNull
        private AdditionalModification additionalModification;
        @Valid
        @NotNull
        private PostDurationExtension postDurationExtension;
        @Valid
        @NotNull
        private Refinement refinement;
        @Valid
        @NotNull
        private OriginFile originFile;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdditionalModification {
        @ValidEnum(enumClass = ProvisionType.class)
        private ProvisionType provisionType;
        private int workingDays;
        private int price;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdditionalPanel {
        @ValidEnum(enumClass = ProvisionType.class)
        private ProvisionType provisionType;
        private int workingDays;
        private int price;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostDurationExtension {
        @ValidEnum(enumClass = ProvisionType.class)
        private ProvisionType provisionType;
        private int price;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OriginFile {
        @ValidEnum(enumClass = ProvisionType.class)
        private ProvisionType provisionType;
        private int price;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Refinement {
        @ValidEnum(enumClass = ProvisionType.class)
        private ProvisionType provisionType;
        private int price;
    }
}
