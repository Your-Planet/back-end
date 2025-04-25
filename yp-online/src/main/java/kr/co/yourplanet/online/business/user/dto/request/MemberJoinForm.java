package kr.co.yourplanet.online.business.user.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.enums.MemberType;
import lombok.Getter;

@Getter
public class MemberJoinForm {

    @Valid
    @NotNull(message = "멤버 기본 정보 요청은 null일 수 없습니다.")
    private BaseJoinForm baseJoinForm;

    @Valid
    private CreatorJoinForm creatorJoinForm;

    @AssertTrue(message = "선택한 멤버 유형에 해당하는 가입 정보를 입력해야 합니다.")
    @JsonIgnore
    private boolean isValidCreatorRequest() {
        return !MemberType.CREATOR.equals(baseJoinForm.getMemberType()) || creatorJoinForm != null;
    }
}
