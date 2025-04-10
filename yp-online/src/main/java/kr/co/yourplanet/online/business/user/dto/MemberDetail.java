package kr.co.yourplanet.online.business.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.core.enums.MemberType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetail {
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    private GenderType genderType;
    private String tel;
    @NotNull
    private MemberType memberType;
    private TermsForm termsForm;
    private String instagramUsername;
    private String instagramAccessToken;
}
