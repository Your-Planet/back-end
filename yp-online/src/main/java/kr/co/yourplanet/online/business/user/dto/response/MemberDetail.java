package kr.co.yourplanet.online.business.user.dto.response;

import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.online.business.user.dto.request.TermsForm;
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
    private TermsForm termsForm;
    private String instagramUsername;
    private String instagramAccessToken;
}
