package kr.co.yourplanet.ypbackend.business.user.domain;

import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import kr.co.yourplanet.ypbackend.common.enums.GenderType;
import kr.co.yourplanet.ypbackend.common.interfaces.ValidEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    // Required
    @Id @GeneratedValue
    private Long id;
    @Column(unique=true) @NotBlank
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

    // Common
    private LocalDateTime termsAgreedTimestamp;
}
