package kr.co.yourplanet.online.business.user.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class TermsForm {

    @AssertTrue(message = "약관에 동의해야 합니다.")
    @NotNull(message = "서비스 이용 약관을 확인해주세요.")
    private Boolean isTermsOfService;

    @AssertTrue(message = "약관에 동의해야 합니다.")
    @NotNull(message = "개인정보 처리방침을 확인해주세요.")
    private Boolean isPrivacyPolicy;

    @NotNull(message = "정보성 수신 동의를 확인해주세요.")
    private Boolean isShoppingInformation;
}