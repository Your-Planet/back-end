package kr.co.yourplanet.ypbackend.business.user.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class TermsInfo {

    private Boolean isTermsOfService;
    private Boolean isPrivacyPolicy;
    private Boolean isShoppingInformation;

}
