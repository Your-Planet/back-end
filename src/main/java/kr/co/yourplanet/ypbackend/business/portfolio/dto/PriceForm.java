package kr.co.yourplanet.ypbackend.business.portfolio.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceForm {
    @Valid
    @NotNull
    public DefaultOption defaultOption;
    @Valid
    @NotNull
    public AdditionalPriceForm additionalOption;
}
