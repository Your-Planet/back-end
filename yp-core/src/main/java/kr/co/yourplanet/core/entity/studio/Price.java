package kr.co.yourplanet.core.entity.studio;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.enums.ProvisionType;
import kr.co.yourplanet.core.enums.PostDurationMonthType;
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
public class Price extends BasicColumn {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", referencedColumnName = "id")
    private Profile profile;

    @Column(name = "price")
    private int price;
    @Column(name = "working_days")
    private int workingDays;
    @Column(name = "modification_count")
    private int modificationCount;
    @Column(name = "cuts")
    private int cuts;
    @Column(name = "post_duration_type")
    private PostDurationMonthType postDurationType;

    @Column(name = "additional_cut_option_type")
    private ProvisionType additionalCutOptionType;
    @Column(name = "additional_modification_option_type")
    private ProvisionType additionalModificationOptionType;
    @Column(name = "additional_origin_file_option_type")
    private ProvisionType additionalOriginFileOptionType;
    @Column(name = "additional_refinement_option_type")
    private ProvisionType additionalRefinementOptionType;
    @Column(name = "additional_post_duration_extension_type")
    private ProvisionType additionalPostDurationExtensionType;

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

    @Column(name = "is_latest")
    private boolean isLatest;


    public void updateDefaultOption(int price, int workingDays, int modificationCount, int cuts, PostDurationMonthType postDurationType) {
        this.price = price;
        this.workingDays = workingDays;
        this.modificationCount = modificationCount;
        this.cuts = cuts;
        this.postDurationType = postDurationType;
    }

    public void updateAdditionalModificationOption(int price, int workingDays, ProvisionType provisionType) {
        this.additionalModificationOptionType = provisionType;

        if (provisionType == ProvisionType.DEFAULT) {
            this.modificationOptionWorkingDays = 0;
            this.modificationOptionPrice = 0;
        } else if (provisionType == ProvisionType.UNPROVIDED) {
            this.modificationOptionWorkingDays = 0;
            this.modificationOptionPrice = 0;
        } else {
            this.modificationOptionWorkingDays = workingDays;
            this.modificationOptionPrice = price;
        }
    }

    public void updateAdditionalCutOption(int price, int workingDays, ProvisionType provisionType) {
        this.additionalCutOptionType = provisionType;

        if (provisionType == ProvisionType.DEFAULT) {
            this.cutOptionWorkingDays = 0;
            this.cutOptionPrice = 0;
        } else if (provisionType == ProvisionType.UNPROVIDED) {
            this.cutOptionWorkingDays = 0;
            this.cutOptionPrice = 0;
        } else {
            this.cutOptionWorkingDays = workingDays;
            this.cutOptionPrice = price;
        }
    }

    public void updateRefinementOption(int price, ProvisionType provisionType) {
        this.additionalRefinementOptionType = provisionType;

        if (provisionType == ProvisionType.DEFAULT) {
            this.refinementPrice = 0;
        } else if (provisionType == ProvisionType.UNPROVIDED) {
            this.refinementPrice = 0;
        } else {
            this.refinementPrice = price;
        }
    }

    public void updateOriginFileOption(int price, ProvisionType provisionType) {
        this.additionalOriginFileOptionType = provisionType;

        if (provisionType == ProvisionType.DEFAULT) {
            this.originFileOptionPrice = 0;
        } else if (provisionType == ProvisionType.UNPROVIDED) {
            this.originFileOptionPrice = 0;
        } else {
            this.originFileOptionPrice = price;
        }
    }

    public void updatePostDurationExtensionOption(int price, ProvisionType provisionType) {
        this.additionalPostDurationExtensionType = provisionType;

        if (provisionType == ProvisionType.DEFAULT) {
            this.postDurationExtensionPrice = 0;
        } else if (provisionType == ProvisionType.UNPROVIDED) {
            this.postDurationExtensionPrice = 0;
        } else {
            this.postDurationExtensionPrice = price;
        }
    }

    public void markAsNotLatest() {
        this.isLatest = false;
    }
}
