package kr.co.yourplanet.online.business.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.yourplanet.core.entity.member.AgreementInfo;
import kr.co.yourplanet.core.entity.member.BusinessInfo;
import kr.co.yourplanet.core.entity.member.Member;
import kr.co.yourplanet.core.entity.member.SettlementInfo;
import kr.co.yourplanet.core.enums.BusinessType;
import kr.co.yourplanet.core.enums.MemberType;
import kr.co.yourplanet.core.enums.StatusCode;
import kr.co.yourplanet.online.business.file.service.FileQueryService;
import kr.co.yourplanet.online.business.user.dto.request.FindIdForm;
import kr.co.yourplanet.online.business.user.dto.response.MemberDetail;
import kr.co.yourplanet.online.business.user.dto.response.MemberFullInfo;
import kr.co.yourplanet.online.business.user.dto.request.TermsForm;
import kr.co.yourplanet.online.business.user.repository.MemberRepository;
import kr.co.yourplanet.online.common.exception.BusinessException;
import kr.co.yourplanet.online.common.util.MaskingUtil;
import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberQueryService {

    private final FileQueryService fileQueryService;
    private final MemberRepository memberRepository;

    public Member getById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "요청한 멤버를 찾을 수 없습니다.", true));
    }

    public Member getIdByEmail(String email) {
        return memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new BusinessException(StatusCode.NOT_FOUND, "해당 이메일로 등록된 멤버를 찾을 수 없습니다.", true));
    }

    public String findEmail(FindIdForm accountRecoveryFrom) {
        Optional<Member> findMember = memberRepository.findByNameAndPhone(accountRecoveryFrom.getName(), accountRecoveryFrom.getTel());

        if (findMember.isEmpty()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "가입된 회원이 없습니다.", false);
        }

        return findMember.get().getEmail();
    }

    public MemberDetail getSummaryInfo(Long memberId) {
        Optional<Member> findMember = memberRepository.findById(memberId);

        if (findMember.isEmpty()) {
            throw new BusinessException(StatusCode.BAD_REQUEST, "해당 회원이 존재하지 않습니다.", false);
        }

        Member member = findMember.get();
        AgreementInfo agreementInfo = member.getAgreementInfo();

        TermsForm termsForm = TermsForm.builder()
                .isTermsOfService(agreementInfo.getTermsOfServiceAgreedTime() != null)
                .isPrivacyPolicy(agreementInfo.getPrivacyPolicyAgreedTime() != null)
                .isShoppingInformation(agreementInfo.getShoppingInformationAgreedTime() != null)
                .build();

        return MemberDetail.builder()
                .email(member.getEmail())
                .name(member.getName())
                .genderType(member.getGenderType())
                .tel(member.getTel())
                .memberType(member.getMemberType())
                .instagramUsername(member.getInstagramInfo().getInstagramUsername())
                .termsForm(termsForm)
                .build();
    }

    public MemberFullInfo getFullInfo(Long memberId) {
        Member member = getById(memberId);

        SettlementInfo settlementInfo = member.getSettlementInfo();
        BusinessInfo businessInfo = member.getBusinessInfo();

        boolean isCreator = MemberType.CREATOR.equals(member.getMemberType());
        boolean isSettlementExist = member.hasSettlementInfo();
        boolean isBusiness = BusinessType.BUSINESS.equals(member.getBusinessType());

        return MemberFullInfo.builder()
                .id(member.getId())
                .memberType(member.getMemberType())
                .instagramUsername(isCreator ? member.getInstagramInfo().getInstagramUsername() : null)
                .email(member.getEmail())
                .businessType(member.getBusinessType())
                .name(member.getName())
                .tel(member.getTel())
                .birthDate(member.getMemberBasicInfo().getBirthDate())
                .genderType(member.getGenderType())
                .companyName(isBusiness ? businessInfo.getCompanyName() : null)
                .businessNumber(isBusiness ? businessInfo.getBusinessNumber() : null)
                .representativeName(isBusiness ? businessInfo.getRepresentativeName() : null)
                .businessAddress(isBusiness ? businessInfo.getBusinessAddress() : null)
                .businessAddressDetail(isBusiness ? businessInfo.getBusinessAddressDetail() : null)
                .bankName(isSettlementExist ? settlementInfo.getBankName() : null)
                .accountHolder(isSettlementExist ? settlementInfo.getAccountHolder() : null)
                .accountNumber(isSettlementExist ? settlementInfo.getAccountNumber() : null)
                .maskedRrn(isSettlementExist && !isBusiness ? MaskingUtil.maskRRN(settlementInfo.getRrn()) : null)
                .bankAccountCopyFile(isSettlementExist && isBusiness ? fileQueryService.getFileMetaDataInfo(settlementInfo.getBankAccountCopyFileId()) : null)
                .businessLicenseFile(isSettlementExist && isBusiness ? fileQueryService.getFileMetaDataInfo(settlementInfo.getBusinessLicenseFileId()) : null)
                .build();
    }
}
