package kr.co.yourplanet.ypbackend.business.portfolio.domain;

import kr.co.yourplanet.ypbackend.common.enums.PriceOptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.Duration;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Price {
    @Id
    @NotBlank
    @Column(name = "id")
    private Long id;
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private Studio studio;

    @Column(name = "basic_price")
    private int basicPrice;
    @Column(name = "basic_work_period")
    private Duration basicWorkPeriod;
    @Column(name = "basic_modification_number")
    private int basicModificationNumber;
    @Column(name = "basic_cuts")
    private int basicCuts;
    @Column(name = "basic_upload_period")
    private Duration basicUploadPeriod;
    @Column(name = "cut_option_type")
    private PriceOptionType cutOptionType;
    @Column(name = "modification_option_type")
    private PriceOptionType modificationOptionType;
    @Column(name = "origin_file_option_type")
    private PriceOptionType originFileOptionType;
    @Column(name = "secondary_utilization_option_type")
    private PriceOptionType secondaryUtilizationOptionType;
    @Column(name = "upload_period_option_type")
    private PriceOptionType uploadPeriodOptionType;
}
