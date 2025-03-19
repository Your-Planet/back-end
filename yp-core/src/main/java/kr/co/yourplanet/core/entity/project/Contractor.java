package kr.co.yourplanet.core.entity.project;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class Contractor {

    @Comment("상호 또는 명칭")
    @Column(nullable = true)
    private String companyName;

    @Comment("사업자 등록번호 혹은 주민번호")
    private String registrationNumber;

    @Comment("주소")
    private String address;

    @Comment("대표자 성명")
    private String representativeName;
}
