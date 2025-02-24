package kr.co.yourplanet.core.entity.member;

import org.hibernate.annotations.Comment;

import jakarta.persistence.Embeddable;
import kr.co.yourplanet.core.enums.BusinessType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(access = AccessLevel.PRIVATE)
public class SettlementInfo {

    private String bankName;
    private String accountHolder;
    private String accountNumber;

    @Comment("주민등록번호 (Resident Registration Number)")
    private String rrn;

    @Comment("통장 사본 img URL")
    private String bankAccountCopyUrl;

    @Comment("사업자 등록증 img URL")
    private String businessLicenseUrl;

    public static SettlementInfo create(BusinessType businessType, String bankName, String accountHolder,
            String accountNumber, String bankAccountCopyUrl, String businessLicenseUrl, String rrn) {
        SettlementInfoBuilder builder = SettlementInfo.builder()
                .bankName(bankName)
                .accountHolder(accountHolder)
                .accountNumber(accountNumber);

        switch (businessType) {
            case BUSINESS -> builder
                    .bankAccountCopyUrl(bankAccountCopyUrl)
                    .businessLicenseUrl(businessLicenseUrl);

            case INDIVIDUAL -> builder
                    .rrn(rrn);
        }

        return builder.build();
    }
}
