package kr.co.yourplanet.ypbackend.business.user.dto;

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
    private boolean sex; // false : 남자 / true : 여자
    @NotBlank
    private String tel;
    @NotBlank
    private String role; // UserRole Enum 객체 생성하자
    @NotBlank
    private String birthYmd; // YYYYMMDD

    // Author
    private String instagramId;

    // Advertiser
    private String companyName;
    private String businessNumber;
    private String representativeName;
    private String businessAddress;

}
