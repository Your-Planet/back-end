package kr.co.yourplanet.ypbackend.business.user.dto;

import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import kr.co.yourplanet.ypbackend.common.enums.GenderType;
import kr.co.yourplanet.ypbackend.common.interfaces.ValidEnum;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class JoinForm {

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
    private String instagramUserName;

    // Advertiser
    private String companyName;
    private String businessNumber;
    private String representativeName;
    private String businessAddress;

    // Commons
    private Boolean isTermsOfService;
    private Boolean isPrivacyPolicy;
    private Boolean isShoppingInformation;

    public JoinForm(){
        isTermsOfService = false;
        isPrivacyPolicy = false;
        isShoppingInformation = false;
    }

}
