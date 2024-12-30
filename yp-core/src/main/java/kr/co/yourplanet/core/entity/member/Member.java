package kr.co.yourplanet.core.entity.member;

import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.core.enums.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BasicColumn {

    // Required
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotBlank
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

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberSalt memberSalt;

    // Author
    private String instagramId;
    private String instagramUsername;
    private String instagramAccessToken;

    // Sponsor
    private String companyName;
    private String businessNumber;
    private String representativeName;
    private String businessAddress;

    // Commons
    @NotNull
    private LocalDateTime termsOfServiceAgreedTime;
    @NotNull
    private LocalDateTime privacyPolicyAgreedTime;
    private LocalDateTime shoppingInformationAgreedTime;

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
