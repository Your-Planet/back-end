package kr.co.yourplanet.online.business.user.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
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

    @AssertTrue(message = "기본 정보의 사업자 여부와 정산 정보의 사업자 여부가 다릅니다.")
    @JsonIgnore
    private boolean isBusinessTypeMatches() {
        if (creatorUpdateForm != null && creatorUpdateForm.getSettlementForm() != null) {
            return baseUpdateForm.getBusinessType() == creatorUpdateForm.getSettlementForm().getBusinessType();
        }
        return true;
    }
}
