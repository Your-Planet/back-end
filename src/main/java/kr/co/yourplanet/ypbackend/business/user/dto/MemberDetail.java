package kr.co.yourplanet.ypbackend.business.user.dto;

import kr.co.yourplanet.ypbackend.common.enums.GenderType;
import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetail {
    private String email;
    private String name;
    private GenderType genderType;
    private String tel;
    private MemberType memberType;
    private TermsInfo termsInfo;
    private String instagramUserName;
    private String instagramAccessToken;
}
