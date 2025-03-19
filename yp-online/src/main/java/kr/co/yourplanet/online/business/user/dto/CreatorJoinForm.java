package kr.co.yourplanet.online.business.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.core.validation.annotation.ValidEnum;
import lombok.Getter;

@Getter
public class CreatorJoinForm {

    @ValidEnum(enumClass = GenderType.class)
    private GenderType genderType;

    @Valid
    @NotNull(message = "인스타그램 계정 정보는 null일 수 없습니다.")
    private InstagramForm instagramForm;
}
