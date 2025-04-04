package kr.co.yourplanet.stub;

import java.time.LocalDate;

import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.GenderType;
import kr.co.yourplanet.online.business.user.dto.BusinessForm;
import kr.co.yourplanet.online.business.user.dto.update.BaseUpdateForm;
import kr.co.yourplanet.online.business.user.dto.update.CreatorUpdateForm;
import kr.co.yourplanet.online.business.user.dto.update.MemberUpdateForm;
import kr.co.yourplanet.online.business.user.dto.update.SettlementForm;

public class MemberFormBuilder {

    /**
     * 공통
     */

    // 기본 정보
    private static BaseUpdateForm individualBaseUpdateForm() {
        return BaseUpdateForm.builder()
                .name("개인")
                .tel("010-1234-5678")
                .birthDate(LocalDate.of(1990, 5, 10))
                .businessType(BusinessType.INDIVIDUAL)
                .build();
    }

    private static BaseUpdateForm businessBaseUpdateForm() {
        return BaseUpdateForm.builder()
                .name("사업자")
                .tel("010-1234-5678")
                .birthDate(LocalDate.of(1990, 5, 10))
                .businessType(BusinessType.BUSINESS)
                .businessForm(createBusinessForm())
                .build();
    }

    // 사업자 정보
    private static BusinessForm createBusinessForm() {
        return BusinessForm.builder()
                .companyName("YourPlanet Inc.")
                .businessNumber("123-45-67890")
                .representativeName("대표자 이름")
                .businessAddress("서울특별시 서초구 서초대로")
                .businessAddressDetail("3층 302호")
                .build();
    }

    /**
     * 광고주
     */
    public static MemberUpdateForm createSponsorIndividualUpdateForm() {
        return MemberUpdateForm.builder()
                .baseUpdateForm(individualBaseUpdateForm())
                .build();
    }

    public static MemberUpdateForm createSponsorBusinessUpdateForm() {
        return MemberUpdateForm.builder()
                .baseUpdateForm(BaseUpdateForm.builder()
                        .name("담당자")
                        .tel("010-1234-5678")
                        .businessType(BusinessType.BUSINESS)
                        .businessForm(createBusinessForm())
                        .build())
                .build();
    }

    /**
     * 작가
     */
    public static MemberUpdateForm createCreatorIndividualUpdateForm() {
        return MemberUpdateForm.builder()
                .baseUpdateForm(individualBaseUpdateForm())
                .creatorUpdateForm(createIndividualCreatorUpdateForm())
                .build();
    }

    public static MemberUpdateForm createCreatorBusinessUpdateForm() {
        return MemberUpdateForm.builder()
                .baseUpdateForm(businessBaseUpdateForm())
                .creatorUpdateForm(createBusinessCreatorUpdateForm())
                .build();
    }

    // 작가 정보
    private static CreatorUpdateForm createIndividualCreatorUpdateForm() {
        return CreatorUpdateForm.builder()
                .genderType(GenderType.MALE)
                .settlementForm(individualSettlementForm())
                .build();
    }

    private static CreatorUpdateForm createBusinessCreatorUpdateForm() {
        return CreatorUpdateForm.builder()
                .genderType(GenderType.MALE)
                .settlementForm(businessSettlementForm())
                .build();
    }

    // 정산 정보
    private static SettlementForm individualSettlementForm() {
        return SettlementForm.builder()
                .businessType(BusinessType.INDIVIDUAL)
                .bankName("신한은행")
                .accountHolder("개인")
                .accountNumber("123-4567-8901")
                .rrn("900510-1234567")
                .build();
    }

    private static SettlementForm businessSettlementForm() {
        return SettlementForm.builder()
                .businessType(BusinessType.BUSINESS)
                .bankName("국민은행")
                .accountHolder("사업자")
                .accountNumber("987-6543-2100")
                .bankAccountCopyUrl("https://example.com/bank-account.jpg")
                .businessLicenseUrl("https://example.com/business-license.jpg")
                .build();
    }
}
