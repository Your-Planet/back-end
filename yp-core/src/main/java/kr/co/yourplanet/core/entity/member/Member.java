package kr.co.yourplanet.core.entity.member;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import kr.co.yourplanet.core.entity.BasicColumn;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.core.enums.MemberType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Member extends BasicColumn {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @SequenceGenerator(name = "member_seq", sequenceName = "member_seq", allocationSize = 10)
    private Long id;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private MemberSalt memberSalt;

    @Enumerated(EnumType.STRING)
    private MemberType memberType;

    @Embedded
    private AccountInfo accountInfo;

    @Embedded
    private MemberBasicInfo memberBasicInfo;

    // for Creator
    @Embedded
    private InstagramInfo instagramInfo;

    @Embedded
    private SettlementInfo settlementInfo;

    @Embedded
    private AgreementInfo agreementInfo;

    // for Business
    @Embedded
    private BusinessInfo businessInfo;


    public static MemberBasicInfo createBasicInfo(MemberType memberType, BusinessType businessType, String name, String tel,
            LocalDate birthDate, GenderType genderType) {
        return MemberBasicInfo.create(memberType, businessType, name, tel, birthDate, genderType);
    }

    public static AccountInfo createAccountInfo(String email, String password) {
        return AccountInfo.create(email, password);
    }

    public void updatePassword(String newPassword) {
        this.accountInfo.updatePassword(newPassword);
    }

    public boolean isCreator() {
        return MemberType.CREATOR.equals(this.getMemberType());
    }

    public boolean isSponsor() {
        return MemberType.SPONSOR.equals(this.getMemberType());
    }

    public boolean hasSettlementInfo() {
        return this.settlementInfo != null;
    }

    // Simplified Getters
    public String getEmail() {
        return this.accountInfo.getEmail();
    }

    public String getPassword() {
        return this.accountInfo.getPassword();
    }

    public String getName() {
        return this.memberBasicInfo.getName();
    }

    public String getTel() {
        return this.memberBasicInfo.getTel();
    }

    public GenderType getGenderType() {
        return this.memberBasicInfo.getGenderType();
    }

    public BusinessType getBusinessType() {
        return this.memberBasicInfo.getBusinessType();
    }
}
