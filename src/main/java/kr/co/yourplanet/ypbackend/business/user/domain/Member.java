package kr.co.yourplanet.ypbackend.business.user.domain;

import kr.co.yourplanet.ypbackend.common.enums.MemberType;
import kr.co.yourplanet.ypbackend.common.enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {

    @Id
    private String id;

    private String password;

    private String name;

    private GenderType genderType; // MALE: 남자 / FEMALE: 여자

    private String tel;

    private MemberType memberType;

    private String birthDate; // YYYYMMDD

    // Author
    private String instagramId;

    // Advertiser
    private String companyName;
    private String businessNumber;
    private String representativeName;
    private String businessAddress;
}
