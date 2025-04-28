package kr.co.yourplanet.online.business.studio.dto;

import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.enums.PostDurationMonthType;
import kr.co.yourplanet.core.enums.ProvisionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceInfoWithoutPrice {
    @NotNull
    private Long id;
    @NotNull
    private DefaultOptionWithoutPrice service;
    @NotNull
    private AdditionalPriceFormWithoutPrice option;

    public static PriceInfoWithoutPrice from(PriceInfo priceInfo, Long priceId) {
        return PriceInfoWithoutPrice.builder()
                .id(priceId)
                .service(new DefaultOptionWithoutPrice(priceInfo.getService()))
                .option(new AdditionalPriceFormWithoutPrice(priceInfo.getOption()))
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class DefaultOptionWithoutPrice {
        private int workingDays;
        private int defaultCuts;
        private int modificationCount;
        private PostDurationMonthType postDurationMonthType;

        public DefaultOptionWithoutPrice(PriceInfo.DefaultOption defaultOption) {
            this.workingDays = defaultOption.getWorkingDays();
            this.defaultCuts = defaultOption.getDefaultCuts();
            this.modificationCount = defaultOption.getModificationCount();
            this.postDurationMonthType = defaultOption.getPostDurationMonthType();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdditionalPriceFormWithoutPrice {
        private AdditionalPanelWithoutPrice additionalPanel;
        private AdditionalModificationWithoutPrice additionalModification;
        private PostDurationExtensionWithoutPrice postDurationExtension;
        private RefinementWithoutPrice refinement;
        private OriginFileWithoutPrice originFile;

        public AdditionalPriceFormWithoutPrice(PriceInfo.AdditionalPriceForm form) {
            this.additionalPanel = new AdditionalPanelWithoutPrice(form.getAdditionalPanel());
            this.additionalModification = new AdditionalModificationWithoutPrice(form.getAdditionalModification());
            this.postDurationExtension = new PostDurationExtensionWithoutPrice(form.getPostDurationExtension());
            this.refinement = new RefinementWithoutPrice(form.getRefinement());
            this.originFile = new OriginFileWithoutPrice(form.getOriginFile());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdditionalModificationWithoutPrice {
        private ProvisionType provisionType;
        private int workingDays;

        public AdditionalModificationWithoutPrice(PriceInfo.AdditionalModification modification) {
            this.provisionType = modification.getProvisionType();
            this.workingDays = modification.getWorkingDays();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class AdditionalPanelWithoutPrice {
        private ProvisionType provisionType;
        private int workingDays;

        public AdditionalPanelWithoutPrice(PriceInfo.AdditionalPanel panel) {
            this.provisionType = panel.getProvisionType();
            this.workingDays = panel.getWorkingDays();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PostDurationExtensionWithoutPrice {
        private ProvisionType provisionType;

        public PostDurationExtensionWithoutPrice(PriceInfo.PostDurationExtension extension) {
            this.provisionType = extension.getProvisionType();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class OriginFileWithoutPrice {
        private ProvisionType provisionType;

        public OriginFileWithoutPrice(PriceInfo.OriginFile originFile) {
            this.provisionType = originFile.getProvisionType();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RefinementWithoutPrice {
        private ProvisionType provisionType;

        public RefinementWithoutPrice(PriceInfo.Refinement refinement) {
            this.provisionType = refinement.getProvisionType();
        }
    }
}