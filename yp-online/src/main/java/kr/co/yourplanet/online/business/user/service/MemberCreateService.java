package kr.co.yourplanet.online.business.user.service;

import org.springframework.stereotype.Service;

import kr.co.yourplanet.core.entity.member.AgreementInfo;
import kr.co.yourplanet.core.entity.member.BusinessInfo;
import kr.co.yourplanet.core.entity.member.InstagramInfo;
import kr.co.yourplanet.core.entity.member.SettlementInfo;
import kr.co.yourplanet.online.business.user.dto.BusinessForm;
import kr.co.yourplanet.online.business.user.dto.InstagramForm;
import kr.co.yourplanet.online.business.user.dto.TermsForm;
import kr.co.yourplanet.online.business.user.dto.update.SettlementForm;

@Service
public class MemberCreateService {

    public BusinessInfo createBusinessInfo(BusinessForm businessForm) {
        return BusinessInfo.create(businessForm.companyName(), businessForm.businessNumber(), businessForm.representativeName(),
                businessForm.businessAddress(), businessForm.businessAddressDetail());
    }

    public AgreementInfo createAgreementInfo(TermsForm termsForm) {
        return AgreementInfo.create(
                termsForm.getIsTermsOfService(),
                termsForm.getIsPrivacyPolicy(),
                termsForm.getIsShoppingInformation()
        );
    }

    public SettlementInfo createSettlementInfo(SettlementForm settlementForm) {
        return SettlementInfo.create(
                settlementForm.getBusinessType(),
                settlementForm.getBankName(),
                settlementForm.getAccountHolder(),
                settlementForm.getAccountNumber(),
                settlementForm.getBankAccountCopyFileId(),
                settlementForm.getBusinessLicenseFileId(),
                settlementForm.getRrn()
        );
    }

    public InstagramInfo createInstagramInfo(InstagramForm instagramForm) {
        return InstagramInfo.create(
                instagramForm.instagramId(),
                instagramForm.instagramUsername(),
                instagramForm.instagramAccessToken()
        );
    }
}
