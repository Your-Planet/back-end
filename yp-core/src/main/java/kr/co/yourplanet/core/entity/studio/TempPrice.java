package kr.co.yourplanet.core.entity.studio;

import kr.co.yourplanet.core.entity.member.Member;
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
public class TempPrice {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "id", unique = true)
    private Member member;

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

    public void updateDefaultOption(int price, int workingDays, int modificationCount, int cuts, PostDurationMonthType postDurationType) {
        this.price = price;
        this.workingDays = workingDays;
        this.modificationCount = modificationCount;
        this.cuts = cuts;
        this.postDurationType = postDurationType;
    }

    public void updateAdditionalModificationOption(int price, int workingDays, ProvisionType provisionType) {
        this.additionalModificationOptionType = provisionType;
        this.modificationOptionWorkingDays = workingDays;
        this.modificationOptionPrice = price;
    }

    public void updateAdditionalCutOption(int price, int workingDays, ProvisionType provisionType) {
        this.additionalCutOptionType = provisionType;
        this.cutOptionWorkingDays = workingDays;
        this.cutOptionPrice = price;
    }

    public void updateRefinementOption(int price, ProvisionType provisionType) {
        this.additionalRefinementOptionType = provisionType;
        this.refinementPrice = price;
    }

    public void updateOriginFileOption(int price, ProvisionType provisionType) {
        this.additionalOriginFileOptionType = provisionType;
        this.originFileOptionPrice = price;
    }

    public void updatePostDurationExtensionOption(int price, ProvisionType provisionTyp) {
        this.additionalPostDurationExtensionType = provisionTyp;
        this.postDurationExtensionPrice = price;
    }
}
