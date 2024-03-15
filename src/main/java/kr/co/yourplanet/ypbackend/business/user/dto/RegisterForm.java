package kr.co.yourplanet.ypbackend.business.user.dto;

import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import kr.co.yourplanet.ypbackend.common.enums.GenderType;
import kr.co.yourplanet.ypbackend.common.interfaces.ValidEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter @NoArgsConstructor
public class RegisterForm {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @ValidEnum(enumClass = GenderType.class)
    private GenderType genderType; // MALE: 남자 / FEMALE: 여자
    @NotBlank
    private String tel;
    @ValidEnum(enumClass = MemberType.class)
    private MemberType memberType;
    @NotBlank
    private String birthDate; // YYYYMMDD

    // Author
    private String instagramId;

    // Advertiser
    private String companyName;
    private String businessNumber;
    private String representativeName;
    private String businessAddress;

}
