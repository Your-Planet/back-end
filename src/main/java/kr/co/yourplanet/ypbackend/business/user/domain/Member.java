package kr.co.yourplanet.ypbackend.business.user.domain;

<<<<<<< HEAD
import kr.co.yourplanet.ypbackend.common.enums.MemberType;
<<<<<<< HEAD
import kr.co.yourplanet.ypbackend.common.enums.GenderType;
=======
import kr.co.yourplanet.ypbackend.common.ENUM.MemberType;
import kr.co.yourplanet.ypbackend.common.ENUM.Sex;
>>>>>>> f70981d (feat: register form 수정)
=======
import kr.co.yourplanet.ypbackend.common.enums.Gender;
>>>>>>> 965ebc6 (sex -> gender 변경)
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

<<<<<<< HEAD
<<<<<<< HEAD
    private GenderType genderType; // MALE: 남자 / FEMALE: 여자
=======
    private Sex sex; // M: 남자 / F: 여자
>>>>>>> f70981d (feat: register form 수정)
=======
    private Gender gender; // MALE: 남자 / FEMALE: 여자
>>>>>>> 965ebc6 (sex -> gender 변경)

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
