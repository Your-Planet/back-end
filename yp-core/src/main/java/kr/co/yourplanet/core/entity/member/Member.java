package kr.co.yourplanet.core.entity.member;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BasicColumn {

    // Required
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 10)
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
