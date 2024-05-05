package kr.co.yourplanet.online.business.portfolio.dto;

import kr.co.yourplanet.core.enums.UploadPeriod;
import kr.co.yourplanet.core.enums.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
