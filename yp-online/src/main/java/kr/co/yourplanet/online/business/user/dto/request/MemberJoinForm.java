package kr.co.yourplanet.online.business.user.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MemberJoinForm {

    @Valid
    @NotNull(message = "멤버 기본 정보는 null일 수 없습니다.")
    private BaseJoinForm baseJoinForm;

    @Valid
    private CreatorJoinForm creatorJoinForm;
}
