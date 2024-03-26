package kr.co.yourplanet.ypbackend.business.user.dto;

import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import kr.co.yourplanet.ypbackend.common.enums.GenderType;
import kr.co.yourplanet.ypbackend.common.interfaces.ValidEnum;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
public class JoinForm {

    @NotBlank
    @Pattern(regexp = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,3}$", message = "이메일 주소 양식을 확인해주세요")
    private String email;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String password;
    @NotBlank(message = "이름을 입력해주세요")
    private String name;
    @ValidEnum(enumClass = GenderType.class)
    private GenderType genderType; // MALE: 남자 / FEMALE: 여자
    @NotBlank(message = "전화번호를 입력해주세요")
    private String tel;
    @ValidEnum(enumClass = MemberType.class)
    private MemberType memberType;
    private String birthDate; // YYYYMMDD

    // Author
    private String instagramId;
    private String instagramUserName;
    private String instagramAccessToken;

    // Sponsor
    private String companyName;
    private String businessNumber;
    private String representativeName;
    private String businessAddress;

    // Commons
    private Boolean isTermsOfService;
    private Boolean isPrivacyPolicy;
    private Boolean isShoppingInformation;

    public JoinForm() {
        isTermsOfService = false;
        isPrivacyPolicy = false;
        isShoppingInformation = false;
    }

}
