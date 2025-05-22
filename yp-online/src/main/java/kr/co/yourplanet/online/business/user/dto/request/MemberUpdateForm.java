package kr.co.yourplanet.online.business.user.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberUpdateForm {

    @Valid
    @NotNull(message = "기본 정보는 null일 수 없습니다.")
    private BaseUpdateForm baseUpdateForm;

    @Valid
    private CreatorUpdateForm creatorUpdateForm;
}
