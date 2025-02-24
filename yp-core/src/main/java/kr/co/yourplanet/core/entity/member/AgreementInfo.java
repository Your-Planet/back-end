package kr.co.yourplanet.core.entity.member;

import java.time.LocalDateTime;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
@Getter
public class AgreementInfo {

    private LocalDateTime termsOfServiceAgreedTime;
    private LocalDateTime privacyPolicyAgreedTime;
    private LocalDateTime shoppingInformationAgreedTime;

    public static AgreementInfo create(Boolean isTermsOfServiceAgreed, Boolean isPrivacyPolicyAgreed,
            Boolean isShoppingInformationAgreed) {
        return AgreementInfo.builder()
                .termsOfServiceAgreedTime(Boolean.TRUE.equals(isTermsOfServiceAgreed) ? LocalDateTime.now() : null)
                .privacyPolicyAgreedTime(Boolean.TRUE.equals(isPrivacyPolicyAgreed) ? LocalDateTime.now() : null)
                .shoppingInformationAgreedTime(Boolean.TRUE.equals(isShoppingInformationAgreed) ? LocalDateTime.now() : null)
                .build();
    }
}
