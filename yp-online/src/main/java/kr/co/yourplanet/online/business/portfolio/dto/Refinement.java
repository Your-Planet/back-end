package kr.co.yourplanet.online.business.portfolio.dto;

import kr.co.yourplanet.core.enums.PriceOptionType;
import kr.co.yourplanet.core.enums.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refinement {
    @ValidEnum(enumClass = PriceOptionType.class)
    public PriceOptionType provisionType;
    public int price;
}
