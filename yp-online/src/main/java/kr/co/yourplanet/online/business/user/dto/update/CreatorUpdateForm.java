package kr.co.yourplanet.online.business.user.dto.update;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.core.enums.ValidEnum;
import lombok.Getter;

@Getter
public class CreatorUpdateForm {

    @ValidEnum(enumClass = GenderType.class)
    private GenderType genderType;

    @Valid
    @NotNull(message = "정산 정보는 null일 수 없습니다.")
    private SettlementForm settlementForm;
}
