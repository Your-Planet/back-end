package kr.co.yourplanet.ypbackend.business.user.dto;

import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import kr.co.yourplanet.ypbackend.common.enums.GenderType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter @NoArgsConstructor
public class RegisterForm {

    @NotBlank
    private String id;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotBlank
    private GenderType genderType; // MALE: 남자 / FEMALE: 여자
    @NotBlank
    private String tel;
    @NotBlank
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
