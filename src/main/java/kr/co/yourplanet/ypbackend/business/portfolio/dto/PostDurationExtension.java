package kr.co.yourplanet.ypbackend.business.portfolio.dto;

import kr.co.yourplanet.ypbackend.common.enums.PriceOptionType;
import kr.co.yourplanet.ypbackend.common.interfaces.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDurationExtension {
    @ValidEnum(enumClass = PriceOptionType.class)
    public PriceOptionType provisionType;
    public int price;
}
