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
public class AdditionalPriceForm {
    @Valid
    @NotNull
    public AdditionalPanel additionalPanel;
    @Valid
    @NotNull
    public AdditionalModification additionalModification;
    @Valid
    @NotNull
    public PostDurationExtension postDurationExtension;
    @Valid
    @NotNull
    public Refinement refinement;
    @Valid
    @NotNull
    public OriginFile originFile;
}
