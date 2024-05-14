package kr.co.yourplanet.online.business.user.dto;

import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.core.enums.ValidEnum;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotNull(message = "약관 동의여부를 확인해주세요")
    TermsInfo termsInfo;
    private String birthDate; // YYYYMMDD

    // Author
    private String instagramId;
    private String instagramUsername;
    private String instagramAccessToken;

    // Sponsor
    private String companyName;
    private String businessNumber;
    private String representativeName;
    private String businessAddress;

}
