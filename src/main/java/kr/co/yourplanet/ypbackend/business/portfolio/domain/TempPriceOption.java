package kr.co.yourplanet.ypbackend.business.portfolio.domain;

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
public class TempPriceOption {
    @Id
    @NotBlank
    @Column(name = "id")
    private Long id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private Price price;

    @Column(name = "cut_price")
    private int cutPrice;
    @Column(name = "cut_work_period")
    private Duration cutWorkPeriod;
    @Column(name = "modification_price")
    private int modificationPrice;
    @Column(name = "modification_period")
    private Duration modificationPeriod;
    @Column(name = "origin_file_price")
    private int originFilePrice;
    @Column(name = "secondary_utilization_price")
    private int uploadPeriodExtensionPrice;
}
