package kr.co.yourplanet.core.entity.member;

import java.time.LocalDate;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.util.LocalDateStringConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(access = AccessLevel.PROTECTED)
@Getter
public class MemberBasicInfo {

    @Column(nullable = false)
    @Comment("Author: 이름 / Sponsor: 담당자명")
    private String name;

    @Column(nullable = false)
    @Comment("Creator: 휴대폰 번호 / Sponsor: 담당자 연락처")
    private String tel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BusinessType businessType;

    @Convert(converter = LocalDateStringConverter.class)
    private LocalDate birthDate; // YYYYMMDD / 광고주 사업자 제외 전부 포함

    @Enumerated(EnumType.STRING)
    private GenderType genderType; // MALE: 남자 / FEMALE: 여자

    protected static MemberBasicInfo create(MemberType memberType, BusinessType businessType, String name, String tel,
            LocalDate birthDate, GenderType genderType) {
        return switch (memberType) {
            case CREATOR -> MemberBasicInfo.builder()
                    .name(name)
                    .tel(tel)
                    .birthDate(birthDate)
                    .businessType(businessType)
                    .genderType(genderType)
                    .build();

            case SPONSOR -> MemberBasicInfo.builder()
                    .name(name)
                    .tel(tel)
                    .birthDate(businessType.equals(BusinessType.INDIVIDUAL) ? birthDate : null)
                    .businessType(businessType)
                    .build();

            default -> throw new UnsupportedOperationException("아직 구현되지 않은 기능입니다.");
        };
    }
}
