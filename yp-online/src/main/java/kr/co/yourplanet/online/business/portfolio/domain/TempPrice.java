package kr.co.yourplanet.online.business.portfolio.domain;

import kr.co.yourplanet.core.entity.portfolio.Studio;
import kr.co.yourplanet.online.business.portfolio.dto.*;
import kr.co.yourplanet.core.enums.PriceOptionType;
import kr.co.yourplanet.core.enums.UploadPeriod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TempPrice {
    @Id
    @Column(name = "id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Studio studio;

    @Column(name = "price")
    private int price;
    @Column(name = "working_days")
    private int workingDays;
    @Column(name = "modification_count")
    private int modificationCount;
    @Column(name = "cuts")
    private int cuts;
    @Column(name = "post_duration_type")
    private UploadPeriod postDurationType;

    @Column(name = "additional_cut_option_type")
    private PriceOptionType additionalCutOptionType;
    @Column(name = "additional_modification_option_type")
    private PriceOptionType additionalModificationOptionType;
    @Column(name = "additional_origin_file_option_type")
    private PriceOptionType additionalOriginFileOptionType;
    @Column(name = "additional_refinement_option_type")
    private PriceOptionType additionalRefinementOptionType;
    @Column(name = "additional_post_duration_extension_type")
    private PriceOptionType additionalPostDurationExtensionType;

    @Column(name = "cut_option_price")
    private int cutOptionPrice;
    @Column(name = "cut_option_working_days")
    private int cutOptionWorkingDays;
    @Column(name = "modification_option_price")
    private int modificationOptionPrice;
    @Column(name = "modification_option_working_days")
    private int modificationOptionWorkingDays;
    @Column(name = "origin_file_option_price")
    private int originFileOptionPrice;
    @Column(name = "refinement_price")
    private int refinementPrice;
    @Column(name = "post_duration_extension_price")
    private int postDurationExtensionPrice;

    public void updatePriceInfo(PriceForm priceForm) {
        updateDefaultOption(priceForm.getDefaultOption());
        updateAdditionalOption(priceForm.getAdditionalOption());
    }

    public void updateDefaultOption(DefaultOption defaultOption) {
        this.price = defaultOption.getPrice();
        this.workingDays = defaultOption.getWorkingDays();
        this.modificationCount = defaultOption.getModificationCount();
        this.cuts = defaultOption.getDefaultCuts();
        this.postDurationType = defaultOption.getPostDurationType();
    }

    public void updateAdditionalOption(AdditionalPriceForm additionalOption) {
        updateAdditionalModificationOption(additionalOption.getAdditionalModification());
        updateAdditionalCutOption(additionalOption.getAdditionalPanel());
        updateRefinementOption(additionalOption.getRefinement());
        updateOriginFileOption(additionalOption.getOriginFile());
        updatePostDurationExtensionOption(additionalOption.getPostDurationExtension());
    }

    public void updateAdditionalModificationOption(AdditionalModification additionalModification) {
        this.additionalModificationOptionType = additionalModification.getProvisionType();
        this.modificationOptionWorkingDays = additionalModification.getWorkingDays();
        this.modificationOptionPrice = additionalModification.getPrice();
    }

    public void updateAdditionalCutOption(AdditionalPanel additionalPanel) {
        this.additionalCutOptionType = additionalPanel.getProvisionType();
        this.cutOptionWorkingDays = additionalPanel.getWorkingDays();
        this.cutOptionPrice = additionalPanel.getPrice();
    }

    public void updateRefinementOption(Refinement refineMent) {
        this.additionalRefinementOptionType = refineMent.getProvisionType();
        this.refinementPrice = refineMent.getPrice();
    }

    public void updateOriginFileOption(OriginFile originFile) {
        this.additionalOriginFileOptionType = originFile.getProvisionType();
        this.originFileOptionPrice = originFile.getPrice();
    }

    public void updatePostDurationExtensionOption(PostDurationExtension postDurationExtension) {
        this.additionalPostDurationExtensionType = postDurationExtension.getProvisionType();
        this.postDurationExtensionPrice = postDurationExtension.getPrice();
    }
}
