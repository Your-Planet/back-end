package kr.co.yourplanet.online.business.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
@AllArgsConstructor
@Builder
public class TermsInfo {

    private Boolean isTermsOfService;
    private Boolean isPrivacyPolicy;
    private Boolean isShoppingInformation;

}
