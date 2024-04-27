package kr.co.yourplanet.ypbackend.business.portfolio.dto;

import kr.co.yourplanet.ypbackend.common.enums.UploadPeriod;
import kr.co.yourplanet.ypbackend.common.interfaces.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultOption {
    public int price;
    public int workingDays;
    public int defaultCuts;
    public int modificationCount;
    @ValidEnum(enumClass = UploadPeriod.class)
    public UploadPeriod postDurationType;
}
