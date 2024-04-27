package kr.co.yourplanet.ypbackend.business.portfolio.dto;

import kr.co.yourplanet.ypbackend.common.enums.PriceOptionType;
import kr.co.yourplanet.ypbackend.common.interfaces.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdditionalModification {
    @ValidEnum(enumClass = PriceOptionType.class)
    public PriceOptionType provisionType;
    public int workingDays;
    public int price;
}
